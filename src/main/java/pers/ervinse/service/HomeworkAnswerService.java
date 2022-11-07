package pers.ervinse.service;

import pers.ervinse.Dto.HomeworkAnswerDto;
import pers.ervinse.domain.HomeworkAnswer;

import java.util.List;

public interface HomeworkAnswerService {
    List<HomeworkAnswer> selectHomeworkAnswerList(Long homeworkId);

    HomeworkAnswer selectHomeworkAnswerById(Long homeworkAnswerId);

    List<HomeworkAnswer> selectHomeworkAnswerListByConditionInAnd(HomeworkAnswer homeworkAnswer);

    void addHomeworkAnswer(HomeworkAnswerDto homeworkAnswerDto);

    void deleteHomeworkAnswer(HomeworkAnswer homeworkAnswer);

    void updateRate(HomeworkAnswer homeworkAnswer);
}
