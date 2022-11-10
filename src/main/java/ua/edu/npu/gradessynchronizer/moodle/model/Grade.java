package ua.edu.npu.gradessynchronizer.moodle.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class Grade {

    @Id
    private Long id;

    private Long timemodified;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "itemid")
    private GradeItem gradeItem;

}
