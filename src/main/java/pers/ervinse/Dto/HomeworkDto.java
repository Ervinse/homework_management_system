package pers.ervinse.Dto;

import lombok.Data;
import pers.ervinse.domain.Homework;

import java.util.Objects;

@Data
public class HomeworkDto extends Homework {

    private String claseName;

    private String courseName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HomeworkDto that = (HomeworkDto) o;
        return Objects.equals(claseName, that.claseName) && Objects.equals(courseName, that.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), claseName, courseName);
    }

    @Override
    public String toString() {
        return "HomeworkDto{" +
                "claseName='" + claseName + '\'' +
                ", courseName='" + courseName + '\'' +
                "} " + super.toString();
    }
}
