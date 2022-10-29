package pers.ervinse.Dto;

import lombok.Data;
import pers.ervinse.domain.Student;

import java.util.Objects;

@Data
public class StudentDto extends Student {

    private String claseName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StudentDto that = (StudentDto) o;
        return Objects.equals(claseName, that.claseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), claseName);
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "claseName='" + claseName + '\'' +
                "} " + super.toString();
    }
}
