package pers.ervinse.Dto;

import lombok.Data;
import pers.ervinse.domain.HomeworkAnswer;

import java.util.List;
import java.util.Objects;

@Data
public class HomeworkAnswerDto extends HomeworkAnswer {

    private String studentName;

    private List<String> imageUploadNameList;

    private List<String> fileUploadNameList;

    private List<String> fileUploadUserNameList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HomeworkAnswerDto that = (HomeworkAnswerDto) o;
        return Objects.equals(studentName, that.studentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studentName, imageUploadNameList, fileUploadNameList, fileUploadUserNameList);
    }

    @Override
    public String toString() {
        return "HomeworkAnswerDto{" +
                "studentName='" + studentName + '\'' +
                ", imageUploadNameList=" + imageUploadNameList +
                ", fileUploadNameList=" + fileUploadNameList +
                ", fileUploadUserNameList=" + fileUploadUserNameList +
                "} " + super.toString();
    }
}
