package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ervinse.common.R;
import pers.ervinse.domain.Course;
import pers.ervinse.service.CourseService;

@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 根据条件获取课程分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 课程分页
     */
    @GetMapping("/page")
    public R<Page<Course>> getCoursePage(Integer currentPage, Integer pageSize, String searchValue) {
        log.info("CourseController - getCoursePage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Course> coursePage = courseService.selectCoursePage(currentPage, pageSize, searchValue);

        return R.getSuccessInstance(coursePage);
    }
}
