package pers.ervinse.Dto;

import lombok.Data;
import pers.ervinse.domain.HomeworkAnswer;

import java.util.Objects;

@Data
public class HomeworkAnswerDto extends HomeworkAnswer {

    private String studentName;

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
        return Objects.hash(super.hashCode(), studentName);
    }

    @Override
    public String toString() {
        return "HomeworkAnswerDto{" +
                "studentName='" + studentName + '\'' +
                "} " + super.toString();
    }
}
