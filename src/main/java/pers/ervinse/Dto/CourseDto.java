package pers.ervinse.Dto;

import lombok.Data;
import pers.ervinse.domain.Course;

import java.util.Objects;

@Data
public class CourseDto extends Course {

    private String courseTeacherName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CourseDto courseDto = (CourseDto) o;
        return Objects.equals(courseTeacherName, courseDto.courseTeacherName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), courseTeacherName);
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "courseTeacherName='" + courseTeacherName + '\'' +
                "} " + super.toString();
    }
}
