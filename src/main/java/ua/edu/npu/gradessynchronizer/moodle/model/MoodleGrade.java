package ua.edu.npu.gradessynchronizer.moodle.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "mdl_grade_grades")
public class MoodleGrade {

    @Id
    private Long id;

    private Long timemodified;
    private Double finalgrade;

    @ManyToOne
    @JoinColumn(name = "userid")
    @NotFound(action = NotFoundAction.IGNORE)
    private MoodleUser moodleUser;

    @ManyToOne
    @JoinColumn(name = "itemid")
    @NotFound(action = NotFoundAction.IGNORE)
    private MoodleGradeItem moodleGradeItem;

}
