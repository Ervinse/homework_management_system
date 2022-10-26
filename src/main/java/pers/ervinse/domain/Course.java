package pers.ervinse.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    //课程id
    @TableId("id")
    private Long courseId;

    //课程名
    private String courseName;

    //上课地址
    private String courseAddress;

    //课程描述
    private String courseDescription;

    //任课老师id
    private Long courseTeacherId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId) && Objects.equals(courseName, course.courseName) && Objects.equals(courseAddress, course.courseAddress) && Objects.equals(courseDescription, course.courseDescription) && Objects.equals(courseTeacherId, course.courseTeacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, courseAddress, courseDescription, courseTeacherId);
    }
}
