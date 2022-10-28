package pers.ervinse.service;

import pers.ervinse.domain.ClaseCourse;

import java.util.List;

public interface ClaseCourseService {
    List<ClaseCourse> selectClaseCourseByConditionInAnd(ClaseCourse claseCourse);
}
