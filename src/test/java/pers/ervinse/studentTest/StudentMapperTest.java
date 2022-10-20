package pers.ervinse.studentTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.ervinse.domain.Student;
import pers.ervinse.mapper.StudentMapper;

@SpringBootTest
public class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;

    //根据id查找学生
    @Test
    void selectStudentById() {

        Student student = studentMapper.selectById(10011001);
        System.out.println(student);
    }

}
