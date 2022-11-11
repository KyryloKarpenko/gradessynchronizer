package ua.edu.npu.gradessynchronizer.moodle.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "mdl_user")
public class MoodleUser {

    @Id
    private Long id;

    private String username;

}
