package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.Dto.HomeworkDto;
import pers.ervinse.domain.Homework;

import java.util.List;

public interface HomeworkService {
    Page<Homework> selectHomeworkPage(int currentPage, int pageSize, String searchValue);

    void addHomework(HomeworkDto homeworkDto);

    List<Homework> selectHomeworkListByConditionInAnd(Homework homework);
}
