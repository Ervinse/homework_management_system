package pers.ervinse;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.ervinse.domain.Teacher;
import pers.ervinse.mapper.TeacherMapper;

@Slf4j
@SpringBootTest
public class TeacherTest {

    @Autowired
    TeacherMapper teacherMapper;

    //mapper层

    @Test
    void selectTeacherById(){
        Teacher teacher = teacherMapper.selectById(1001);
        System.out.println(teacher);
    }
}
