package pers.ervinse.service;

import pers.ervinse.domain.ClaseCourse;

import java.util.List;

public interface ClaseCourseService {
    ClaseCourse selectClaseCourseById(Long claseCourseId);

    List<ClaseCourse> selectClaseCourseByConditionInAnd(ClaseCourse claseCourse);
}
