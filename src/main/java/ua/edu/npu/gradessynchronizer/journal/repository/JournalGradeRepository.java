package ua.edu.npu.gradessynchronizer.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.npu.gradessynchronizer.journal.model.JournalGrade;
import ua.edu.npu.gradessynchronizer.journal.model.JournalGradeId;

@Repository
public interface JournalGradeRepository extends JpaRepository<JournalGrade, JournalGradeId> {

}
