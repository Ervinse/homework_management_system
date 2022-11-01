package pers.ervinse.Dto;

import lombok.Data;
import pers.ervinse.domain.Homework;

import java.util.List;
import java.util.Objects;

@Data
public class HomeworkDto extends Homework {

    private Long claseId;
    private String claseName;
    private Long courseId;
    private String courseName;

    private String claseTeacherName;
    private List<String> imageUploadNameList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HomeworkDto that = (HomeworkDto) o;
        return Objects.equals(claseId, that.claseId) && Objects.equals(claseName, that.claseName) && Objects.equals(courseId, that.courseId) && Objects.equals(courseName, that.courseName) && Objects.equals(claseTeacherName, that.claseTeacherName) && Objects.equals(imageUploadNameList, that.imageUploadNameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), claseId, claseName, courseId, courseName, claseTeacherName, imageUploadNameList);
    }

    @Override
    public String toString() {
        return "HomeworkDto{" +
                "claseId=" + claseId +
                ", claseName='" + claseName + '\'' +
                ", courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", claseTeacherName='" + claseTeacherName + '\'' +
                ", imageUploadNameList=" + imageUploadNameList +
                "} " + super.toString();
    }
}
