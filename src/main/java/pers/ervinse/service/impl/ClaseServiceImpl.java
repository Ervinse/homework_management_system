package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ervinse.Dto.ClaseDto;
import pers.ervinse.common.CustomException;
import pers.ervinse.domain.Clase;
import pers.ervinse.domain.ClaseCourse;
import pers.ervinse.domain.Course;
import pers.ervinse.mapper.ClaseCourseMapper;
import pers.ervinse.mapper.ClaseMapper;
import pers.ervinse.service.ClaseService;

import java.util.List;

@Slf4j
@Service
public class ClaseServiceImpl implements ClaseService {

    @Autowired
    private ClaseMapper claseMapper;

    @Autowired
    private ClaseCourseMapper claseCourseMapper;

    @Override
    public Page<Clase> selectClasePage(int currentPage, int pageSize, String searchValue) {
        log.info("ClaseService - selectClasePage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Clase> page = new Page<>(currentPage, pageSize);

        //创建条件构造器
        LambdaQueryWrapper<Clase> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.like(StringUtils.isNotEmpty(searchValue), Clase::getClaseName, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Clase::getTimeOfEnrollment, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Clase::getClaseLeaderId, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Clase::getClaseTeacherId, searchValue);
        //添加排序条件
        wrapper.orderByAsc(Clase::getClaseId);

        claseMapper.selectPage(page, wrapper);

        log.info("page信息:current = {},pages = {},size = {},total = {},records = {}", page.getCurrent(), page.getPages(), page.getSize(), page.getTotal(), page.getRecords());

        return page;
    }

    /**
     * 获取班级列表
     *
     * @return 班级列表
     */
    @Override
    public List<Clase> selectClaseList() {
        log.info("ClaseService - selectClaseList");

        return claseMapper.selectList(null);
    }

    /**
     * 添加班级和对应的课程
     *
     * @param claseDto 含有班级添加信息和课程信息的班级传输对象
     */
    @Override
    @Transactional
    public void addClase(ClaseDto claseDto) {
        log.info("ClaseService - addClase : claseDto = {}", claseDto);

        //添加班级
        int affectClaseRows = claseMapper.insert(claseDto);
        int affectCourseRows = 0;

        //获取班级传输对象中的课程集合
        List<Long> courseList = claseDto.getCourseList();
        //为每一个课程创建ClaseCourse,并加上当前班级id
        for (Long courseId: courseList) {
            ClaseCourse claseCourse = new ClaseCourse();
            claseCourse.setClaseId(claseDto.getClaseId());
            claseCourse.setCourseId(courseId);
            //插入ClaseCourse
            affectCourseRows = claseCourseMapper.insert(claseCourse);
        }

        if (affectClaseRows > 0 && affectCourseRows > 0) {
            log.info("添加班级成功,影响了" + affectClaseRows + "条数据");
        } else {
            log.error("添加班级失败,影响了" + affectClaseRows + "条数据");
            throw new CustomException("服务器错误,添加失败!");
        }

    }
}
