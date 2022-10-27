package pers.ervinse.Dto;

import lombok.Data;
import pers.ervinse.domain.Clase;

import java.util.Objects;

@Data
public class ClaseDto extends Clase {

    private String claseTeacherName;

    private String claseLeaderName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClaseDto claseDto = (ClaseDto) o;
        return Objects.equals(claseTeacherName, claseDto.claseTeacherName) && Objects.equals(claseLeaderName, claseDto.claseLeaderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), claseTeacherName, claseLeaderName);
    }
}
