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

    private List<String> courseList;


}
