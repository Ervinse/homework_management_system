package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.Dto.HomeworkDto;
import pers.ervinse.domain.Homework;

public interface HomeworkService {
    Page<Homework> selectHomeworkPage(int currentPage, int pageSize, String searchValue);

    void addHomework(HomeworkDto homeworkDto);
}
