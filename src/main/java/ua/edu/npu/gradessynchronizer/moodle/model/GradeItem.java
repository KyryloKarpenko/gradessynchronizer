package ua.edu.npu.gradessynchronizer.moodle.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "mdl_grade_items")
public class GradeItem {

    @Id
    private Long id;

    private String itemtype;

    @ManyToOne
    @JoinColumn(name = "courseid")
    private Course course;

}
