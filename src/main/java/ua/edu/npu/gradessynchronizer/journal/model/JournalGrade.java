package ua.edu.npu.gradessynchronizer.journal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MOODLE_GRADES")
@Builder
public class JournalGrade {

    @EmbeddedId
    private JournalGradeId moodleGradeId;

    private Double grade;

}
