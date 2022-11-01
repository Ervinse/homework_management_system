package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.domain.HomeworkAnswer;
import pers.ervinse.mapper.HomeworkAnswerMapper;
import pers.ervinse.service.HomeworkAnswerService;

import java.util.List;

@Slf4j
@Service
public class HomeworkAnswerServiceImpl implements HomeworkAnswerService {

    @Autowired
    private HomeworkAnswerMapper homeworkAnswerMapper;


    @Override
    public List<HomeworkAnswer> selectHomeworkAnswerList(Long homeworkId) {
        log.info("HomeworkAnswerService - selectHomeworkAnswerList :homeworkId = {}", homeworkId);


        LambdaQueryWrapper<HomeworkAnswer> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(homeworkId != null, HomeworkAnswer::getHomeworkId, homeworkId);
        //添加排序条件
        wrapper.orderByAsc(HomeworkAnswer::getHomeworkAnswerId);

        List<HomeworkAnswer> homeworkAnswerList = homeworkAnswerMapper.selectList(wrapper);

        return homeworkAnswerList;
    }

    @Override
    public HomeworkAnswer selectHomeworkAnswerById(Long homeworkAnswerId) {
        log.info("HomeworkAnswerService - selectHomeworkAnswerById :homeworkAnswerId = {}", homeworkAnswerId);

        return homeworkAnswerMapper.selectById(homeworkAnswerId);
    }

}
