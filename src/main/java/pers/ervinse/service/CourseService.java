package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.domain.Course;

public interface CourseService {
    Page<Course> selectCoursePage(int currentPage, int pageSize, String searchValue);

    void addCourse(Course course);
}
