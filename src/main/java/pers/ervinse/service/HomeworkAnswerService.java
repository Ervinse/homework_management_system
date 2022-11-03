package pers.ervinse.service;

import pers.ervinse.Dto.HomeworkAnswerDto;
import pers.ervinse.domain.HomeworkAnswer;

import java.util.List;

public interface HomeworkAnswerService {
    List<HomeworkAnswer> selectHomeworkAnswerList(Long homeworkId);

    HomeworkAnswer selectHomeworkAnswerById(Long homeworkAnswerId);

    void addHomeworkAnswer(HomeworkAnswerDto homeworkAnswerDto);
}
