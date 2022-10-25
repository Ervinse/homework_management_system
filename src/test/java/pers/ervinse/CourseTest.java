package pers.ervinse;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.ervinse.domain.Course;
import pers.ervinse.mapper.CourseMapper;

@Slf4j
@SpringBootTest
public class CourseTest {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    void selectCourseById(){

        Course course = courseMapper.selectById(443096630275642515L);
        System.out.println(course);
    }
}
