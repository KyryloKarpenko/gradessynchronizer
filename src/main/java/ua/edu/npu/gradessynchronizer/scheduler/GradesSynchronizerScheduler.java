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
public class GradesSynchronizerScheduler {

    private final GradeRepository gradeRepository;

    public GradesSynchronizerScheduler(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void synchronizeGrades() {
        List<Grade> grades = gradeRepository.findAllByTimemodifiedBetween(
                LocalDateTime.now().minusMinutes(30).toInstant(ZoneOffset.of("+02:00")).getEpochSecond(),
                LocalDateTime.now().toInstant(ZoneOffset.of("+02:00")).getEpochSecond());
        log.info("Found {} grades for the last 30 minutes", grades.size());
    }

}
