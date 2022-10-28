package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.domain.ClaseCourse;
import pers.ervinse.domain.Student;
import pers.ervinse.mapper.ClaseCourseMapper;
import pers.ervinse.service.ClaseCourseService;

import java.util.List;

@Slf4j
@Service
public class ClaseCourseServiceImpl implements ClaseCourseService {

    @Autowired
    private ClaseCourseMapper claseCourseMapper;

    /**
     * 根据条件查询班级课程对象
     *
     * @param claseCourse 含有查询条件的班级课程对象
     * @return 班级课程对象
     */
    @Override
    public List<ClaseCourse> selectClaseCourseByConditionInAnd(ClaseCourse claseCourse) {
        log.info("ClaseCourseService - selectClaseCourseByConditionInAnd : claseCourse = {}", claseCourse);

        //创建条件构造器
        LambdaQueryWrapper<ClaseCourse> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.eq(claseCourse.getClaseCourseId() != null, ClaseCourse::getClaseCourseId, claseCourse.getClaseCourseId())
                .or()
                .eq(claseCourse.getClaseId() != null, ClaseCourse::getClaseId, claseCourse.getClaseId())
                .or()
                .eq(claseCourse.getCourseId() != null, ClaseCourse::getCourseId, claseCourse.getCourseId());

        return claseCourseMapper.selectList(wrapper);

    }
}
