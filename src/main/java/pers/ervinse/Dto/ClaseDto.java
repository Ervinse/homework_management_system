package pers.ervinse.Dto;

import lombok.Data;
import pers.ervinse.domain.Clase;

import java.util.List;
import java.util.Objects;

@Data
public class ClaseDto extends Clase {

    private String claseTeacherName;

    private String claseLeaderName;

    private List<Long> courseIdList;

    private List<CourseDto> courseList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClaseDto claseDto = (ClaseDto) o;
        return Objects.equals(claseTeacherName, claseDto.claseTeacherName) && Objects.equals(claseLeaderName, claseDto.claseLeaderName) && Objects.equals(courseIdList, claseDto.courseIdList) && Objects.equals(courseList, claseDto.courseList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), claseTeacherName, claseLeaderName, courseIdList, courseList);
    }

    @Override
    public String toString() {
        return "ClaseDto{" +
                "claseTeacherName='" + claseTeacherName + '\'' +
                ", claseLeaderName='" + claseLeaderName + '\'' +
                ", courseIdList=" + courseIdList +
                ", courseList=" + courseList +
                "} " + super.toString();
    }
}
