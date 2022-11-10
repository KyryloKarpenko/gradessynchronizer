package ua.edu.npu.gradessynchronizer.moodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.npu.gradessynchronizer.moodle.model.Grade;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findAllByTimemodifiedBetweenAndGradeItem_Itemtype(Long from, Long to, String itemtype);

}
