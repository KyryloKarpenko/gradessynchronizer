package ua.edu.npu.gradessynchronizer.moodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.npu.gradessynchronizer.moodle.model.MoodleGrade;

import java.util.List;

@Repository
public interface MoodleGradeRepository extends JpaRepository<MoodleGrade, Long> {

    List<MoodleGrade> findAllByFinalgradeNotNullAndTimemodifiedBetweenAndMoodleGradeItem_Itemtype(Long from, Long to, String itemtype);

}
