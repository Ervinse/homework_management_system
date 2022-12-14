package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.exception.BusinessException;
import pers.ervinse.domain.ClaseCourse;
import pers.ervinse.domain.Course;
import pers.ervinse.exception.ProgramException;
import pers.ervinse.mapper.CourseMapper;
import pers.ervinse.service.ClaseCourseService;
import pers.ervinse.service.CourseService;

import java.util.List;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ClaseCourseService claseCourseService;

    /**
     * 根据条件获取课程分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 课程分页
     */
    @Override
    public Page<Course> selectCoursePage(int currentPage, int pageSize, String searchValue) {
        log.info("CourseService - selectCoursePage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Course> page = new Page<>(currentPage, pageSize);

        //创建条件构造器
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.like(StringUtils.isNotEmpty(searchValue), Course::getCourseName, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Course::getCourseAddress, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Course::getCourseDescription, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Course::getCourseTeacherId, searchValue);
        //添加排序条件
        wrapper.orderByAsc(Course::getCourseId);

        courseMapper.selectPage(page, wrapper);

        log.info("page信息:current = {},pages = {},size = {},total = {},records = {}", page.getCurrent(), page.getPages(), page.getSize(), page.getTotal(), page.getRecords());

        return page;
    }

    /**
     * 根据id获取课程
     *
     * @param courseId 课程id
     * @return 课程详情
     */
    @Override
    public Course selectCourseById(Long courseId) {
        log.info("CourseService - selectCourseById :courseId = {}", courseId);

        return courseMapper.selectById(courseId);
    }

    /**
     * 获取课程列表
     *
     * @return 课程列表
     */
    @Override
    public List<Course> selectCourseList() {
        log.info("CourseService - selectCourseList ");

        return courseMapper.selectList(null);
    }

    @Override
    public List<Course> selectCourseByConditionInOR(Course course) {
        log.info("CourseService - selectCourseByConditionInOR : course = {}", course);

        //创建条件构造器
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.eq(course.getCourseId() != null, Course::getCourseId, course.getCourseId())
                .or()
                .like(StringUtils.isNotEmpty(course.getCourseName()), Course::getCourseName, course.getCourseName())
                .or()
                .like(StringUtils.isNotEmpty(course.getCourseAddress()), Course::getCourseAddress, course.getCourseAddress())
                .or()
                .like(StringUtils.isNotEmpty(course.getCourseDescription()), Course::getCourseDescription, course.getCourseDescription())
                .or()
                .like(course.getCourseTeacherId() != null, Course::getCourseTeacherId, course.getCourseTeacherId());

        return courseMapper.selectList(wrapper);
    }

    /**
     * 添加课程
     *
     * @param course 含有课程信息的对象
     */
    @Override
    public void addCourse(Course course) {
        log.info("CourseService - addCourse :course = {}", course);

        int affectRows = courseMapper.insert(course);
        if (affectRows > 0) {
            log.info("添加课程成功,影响了" + affectRows + "条数据");
        } else {
            log.error("添加课程失败,影响了" + affectRows + "条数据");
            throw new ProgramException("服务器错误,添加失败!");
        }
    }

    /**
     * 修改课程
     *
     * @param course 含有课程信息的对象
     */
    @Override
    public void updateCourse(Course course) {
        log.info("CourseService - updateCourse :course = {}", course);


        int affectRows = courseMapper.updateById(course);
        if (affectRows > 0) {
            log.info("修改课程成功,影响了" + affectRows + "条数据");
        } else {
            log.error("修改课程失败,影响了" + affectRows + "条数据");
            throw new ProgramException("服务器错误,添加失败!");
        }
    }

    /**
     * 删除课程
     *
     * @param courseId 课程id
     */
    @Override
    public void deleteCourse(Long courseId) {
        log.info("CourseService - deleteCourse :courseId = {}", courseId);

        ClaseCourse claseCourse = new ClaseCourse();
        claseCourse.setCourseId(courseId);
        List<ClaseCourse> claseCourseList = claseCourseService.selectClaseCourseByConditionInAnd(claseCourse);
        if (claseCourseList.size() > 0){
            throw new BusinessException("有相关班级的课程中含有此课程,无法删除!");
        }

        int affectRows = courseMapper.deleteById(courseId);
        if (affectRows > 0) {
            log.info("删除课程成功,影响了" + affectRows + "条数据");
        } else {
            log.error("删除课程失败,影响了" + affectRows + "条数据");
            throw new ProgramException("服务器错误,删除失败!");
        }
    }
}
