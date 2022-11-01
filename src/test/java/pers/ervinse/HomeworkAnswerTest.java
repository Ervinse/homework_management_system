package pers.ervinse;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.ervinse.domain.HomeworkAnswer;
import pers.ervinse.mapper.HomeworkAnswerMapper;

@Slf4j
@SpringBootTest
public class HomeworkAnswerTest {

    @Autowired
    private HomeworkAnswerMapper homeworkAnswerMapper;

    @Test
    public void selectHomeworkAnswer(){

        HomeworkAnswer homeworkAnswer = homeworkAnswerMapper.selectById(1001);
        System.out.println(homeworkAnswer);

    }
}
