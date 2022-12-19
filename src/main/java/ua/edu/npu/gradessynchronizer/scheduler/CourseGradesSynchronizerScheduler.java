package ua.edu.npu.gradessynchronizer.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.edu.npu.gradessynchronizer.journal.model.JournalGrade;
import ua.edu.npu.gradessynchronizer.journal.model.JournalGradeId;
import ua.edu.npu.gradessynchronizer.journal.repository.JournalGradeRepository;
import ua.edu.npu.gradessynchronizer.moodle.model.MoodleGrade;
import ua.edu.npu.gradessynchronizer.moodle.repository.MoodleGradeRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Component
public class CourseGradesSynchronizerScheduler {

    @Value("${spring.datasource.jdbc-url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private final MoodleGradeRepository moodleGradeRepository;
    private final JournalGradeRepository journalGradeRepository;

    public CourseGradesSynchronizerScheduler(MoodleGradeRepository moodleGradeRepository,
                                             JournalGradeRepository journalGradeRepository) {
        this.moodleGradeRepository = moodleGradeRepository;
        this.journalGradeRepository = journalGradeRepository;
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void synchronizeCourseGrades() throws SQLException {
        List<MoodleGrade> moodleGrades =
                moodleGradeRepository.findAllByFinalgradeNotNullAndTimemodifiedBetweenAndMoodleGradeItem_Itemtype(
                        LocalDateTime.now().minusMinutes(30).toInstant(ZoneOffset.of("+02:00")).getEpochSecond(),
                        LocalDateTime.now().toInstant(ZoneOffset.of("+02:00")).getEpochSecond(), "course");

        if (!moodleGrades.isEmpty()) {
            log.info("Found {} moodle grades for the last 30 minutes", moodleGrades.size());

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                 Statement s = connection.createStatement()) {

                if (!s.executeQuery("select 1 from rdb$relations where rdb$relation_name = 'MOODLE_GRADES'").next()) {
                    log.info("Table MOODLE_GRADES was not found in journal database, will try to create it now");
                    s.execute("CREATE TABLE moodle_grades(course INTEGER NOT NULL,student VARCHAR(100) NOT NULL," +
                            "grade DOUBLE PRECISION NOT NULL);");
                    s.execute("ALTER TABLE moodle_grades ADD PRIMARY KEY (course, student);");
                    log.info("Table MOODLE_GRADES was successfully created in journal database");
                }

                List<JournalGrade> journalGrades = journalGradeRepository.saveAll(moodleGrades.stream()
                        .filter(moodleGrade -> moodleGrade.getMoodleUser() != null &&
                                moodleGrade.getMoodleGradeItem() != null &&
                                moodleGrade.getMoodleGradeItem().getMoodleCourse() != null)
                        .filter(moodleGrade -> moodleGrade.getFinalgrade() != null)
                        .map(moodleGrade -> JournalGrade.builder()
                                .moodleGradeId(JournalGradeId
                                        .builder()
                                        .course(moodleGrade.getMoodleGradeItem().getMoodleCourse().getId())
                                        .student(moodleGrade.getMoodleUser().getUsername())
                                        .build())
                                .grade(moodleGrade.getFinalgrade())
                                .build())
                        .toList());

                log.info("{} moodle grades were successfully migrated to journal", journalGrades.size());
            }
        } else {
            log.info("There are no moodle grades for the last 30 minutes");
        }
    }

}
