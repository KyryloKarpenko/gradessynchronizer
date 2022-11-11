package ua.edu.npu.gradessynchronizer.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.npu.gradessynchronizer.journal.model.JournalGrade;
import ua.edu.npu.gradessynchronizer.journal.model.JournalGradeId;

public interface JournalGradeRepository extends JpaRepository<JournalGrade, JournalGradeId> {

}
