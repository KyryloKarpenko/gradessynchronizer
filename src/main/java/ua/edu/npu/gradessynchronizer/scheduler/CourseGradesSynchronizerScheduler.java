package ua.edu.npu.gradessynchronizer.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.edu.npu.gradessynchronizer.moodle.model.Grade;
import ua.edu.npu.gradessynchronizer.moodle.repository.GradeRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Component
public class CourseGradesSynchronizerScheduler {

    private final GradeRepository gradeRepository;

    public CourseGradesSynchronizerScheduler(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void synchronizeCourseGrades() {
        List<Grade> courseGrades = gradeRepository.findAllByTimemodifiedBetweenAndGradeItem_Itemtype(
                LocalDateTime.now().minusMinutes(30).toInstant(ZoneOffset.of("+02:00")).getEpochSecond(),
                LocalDateTime.now().toInstant(ZoneOffset.of("+02:00")).getEpochSecond(), "course");
        log.info("Found {} course grades for the last 30 minutes", courseGrades.size());
    }

}
