package pers.ervinse.Dto;

import lombok.Data;
import pers.ervinse.domain.Course;

@Data
public class CourseDto extends Course {

    private String courseTeacherName;

    @Override
    public String toString() {
        return "CourseDto{" +
                "courseTeacherName='" + courseTeacherName + '\'' +
                "} " + super.toString();
    }
}
