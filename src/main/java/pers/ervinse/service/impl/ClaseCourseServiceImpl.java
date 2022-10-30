package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.domain.ClaseCourse;
import pers.ervinse.mapper.ClaseCourseMapper;
import pers.ervinse.service.ClaseCourseService;

import java.util.List;

@Slf4j
@Service
public class ClaseCourseServiceImpl implements ClaseCourseService {

    @Autowired
    private ClaseCourseMapper claseCourseMapper;

    /**
     * 根据id获取班级课程对象
     * @param claseCourseId 班级课程id
     * @return 班级课程id
     */
    @Override
    public ClaseCourse selectClaseCourseById(Long claseCourseId){
        log.info("ClaseCourseService - selectClaseCourseById : claseCourseId = {}", claseCourseId);

        return claseCourseMapper.selectById(claseCourseId);
    }

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
                .eq(claseCourse.getClaseId() != null, ClaseCourse::getClaseId, claseCourse.getClaseId())
                .eq(claseCourse.getCourseId() != null, ClaseCourse::getCourseId, claseCourse.getCourseId());

        return claseCourseMapper.selectList(wrapper);

    }
}
