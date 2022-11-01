package pers.ervinse.service;

import pers.ervinse.domain.HomeworkAnswer;

import java.util.List;

public interface HomeworkAnswerService {
    List<HomeworkAnswer> selectHomeworkAnswerList(Long homeworkId);
}
