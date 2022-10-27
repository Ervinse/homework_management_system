package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.ervinse.Dto.CourseDto;
import pers.ervinse.common.R;
import pers.ervinse.domain.Course;
import pers.ervinse.domain.Teacher;
import pers.ervinse.service.CourseService;
import pers.ervinse.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 根据条件获取课程分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 课程传输分页
     */
    @GetMapping("/page")
    public R<Page<CourseDto>> getCoursePage(Integer currentPage, Integer pageSize, String searchValue) {
        log.info("CourseController - getCoursePage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        //查询课程分页
        Page<Course> coursePage = courseService.selectCoursePage(currentPage, pageSize, searchValue);

        //创建课程传输分页,获取课程分页中的分页数据
        Page<CourseDto> courseDtoPage = new Page<>();
        BeanUtils.copyProperties(coursePage, courseDtoPage, "records");

        //获取课程列表
        List<Course> coursePageRecords = coursePage.getRecords();
        //遍历课程列表中的每一个课程对象,获取其对应的课程传输对象,收集为list
        List<CourseDto> courseDtoPageRecords = coursePageRecords.stream().map(course -> {
            //对每一个课程,创建课程传输对象,并将每个课程数据复制到对应的传输对象中
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course, courseDto);
            //对每一个课程,根据教师id获取教师对象,并将教师名字添加到传输对象中
            Teacher teacher = teacherService.selectTeacherById(course.getCourseTeacherId());
            courseDto.setCourseTeacherName(teacher.getTeacherName());

            //返回传输对象
            return courseDto;
        }).collect(Collectors.toList());

        //为课程传输分页添加课程传输对象集合
        courseDtoPage.setRecords(courseDtoPageRecords);

        return R.getSuccessInstance(courseDtoPage);
    }


    /**
     * 根据id获取课程
     *
     * @param courseId 课程id
     * @return 课程详情
     */
    @GetMapping
    public R<Course> getCourseById(String courseId) {
        log.info("CourseController - getCourseById :courseId = {}", courseId);

        Course course = courseService.selectCourseById(courseId);
        return R.getSuccessInstance(course);
    }

    /**
     * 添加课程
     *
     * @param course 含有课程信息和教师信息的课程传输对象
     * @return 添加结果
     */
    @PostMapping
    public R<String> addCourse(@RequestBody Course course) {
        log.info("CourseController - addCourse :course = {}", course);

        courseService.addCourse(course);

        return R.getSuccessOperationInstance();
    }

    /**
     * 修改课程
     *
     * @param course 含有课程信息和教师信息的课程传输对象
     * @return 修改结果
     */
    @PutMapping
    public R<String> updateCourse(@RequestBody Course course) {
        log.info("CourseController - updateCourse :course = {}", course);

        courseService.updateCourse(course);

        return R.getSuccessOperationInstance();
    }

    /**
     * 删除课程
     *
     * @param courseId 课程id
     */
    @DeleteMapping
    public R<String> deleteCourse(Long courseId) {
        log.info("CourseController - deleteCourse :courseId = {}", courseId);

        courseService.deleteCourse(courseId);

        return R.getSuccessOperationInstance();
    }
}
