package pers.ervinse.service;

import pers.ervinse.Dto.HomeworkAnswerDto;
import pers.ervinse.domain.HomeworkAnswer;

import java.util.List;

public interface HomeworkAnswerService {
    List<HomeworkAnswer> selectHomeworkAnswerList(Long homeworkId);

    HomeworkAnswer selectHomeworkAnswerById(Long homeworkAnswerId);

    List<HomeworkAnswer> selectHomeworkAnswerListByConditionInAnd(HomeworkAnswer homeworkAnswer);

    int selectHomeworkAnswerRateByStudentAndHomework(Long homeworkAnswerId, Long studentId);

    void addHomeworkAnswer(HomeworkAnswerDto homeworkAnswerDto);

    void deleteHomeworkAnswer(HomeworkAnswer homeworkAnswer, boolean isCoercive);

    void updateRate(HomeworkAnswer homeworkAnswer);
}
