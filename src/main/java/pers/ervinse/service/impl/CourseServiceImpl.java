package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.domain.Course;
import pers.ervinse.mapper.CourseMapper;
import pers.ervinse.service.CourseService;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

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
                .like(StringUtils.isNotEmpty(searchValue), Course::getCourseDescription, searchValue);
        //添加排序条件
        wrapper.orderByAsc(Course::getCourseId);

        courseMapper.selectPage(page, wrapper);

        log.info("page信息:current = {},pages = {},size = {},total = {},records = {}", page.getCurrent(), page.getPages(), page.getSize(), page.getTotal(), page.getRecords());

        return page;
    }
}