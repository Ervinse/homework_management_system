package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.domain.Homework;
import pers.ervinse.mapper.HomeworkMapper;
import pers.ervinse.service.HomeworkService;

@Slf4j
@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private HomeworkMapper homeworkMapper;

    @Override
    public Page<Homework> selectHomeworkPage(int currentPage, int pageSize, String searchValue) {
        log.info("HomeworkService - selectHomeworkPage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Homework> page = new Page<>(currentPage, pageSize);

        //创建条件构造器
        LambdaQueryWrapper<Homework> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.like(StringUtils.isNotEmpty(searchValue), Homework::getHomeworkName, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Homework::getHomeworkDescription, searchValue);
        //添加排序条件
        wrapper.orderByAsc(Homework::getHomeworkId);

        homeworkMapper.selectPage(page, wrapper);

        log.info("page信息:current = {},pages = {},size = {},total = {},records = {}", page.getCurrent(), page.getPages(), page.getSize(), page.getTotal(), page.getRecords());

        return page;
    }
}
