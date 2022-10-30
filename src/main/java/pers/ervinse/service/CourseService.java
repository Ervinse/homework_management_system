package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.domain.Course;

import java.util.List;

public interface CourseService {
    Page<Course> selectCoursePage(int currentPage, int pageSize, String searchValue);

    Course selectCourseById(Long courseId);

    List<Course> selectCourseList();

    List<Course> selectCourseByConditionInOR(Course course);

    void addCourse(Course course);

    void updateCourse(Course course);

    void deleteCourse(Long courseId);

}
