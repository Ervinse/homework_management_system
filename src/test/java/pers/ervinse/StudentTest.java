package pers.ervinse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.ervinse.domain.Student;
import pers.ervinse.mapper.StudentMapper;

@Slf4j
@SpringBootTest
public class StudentTest {

    @Autowired
    private StudentMapper studentMapper;


    //mapper层

    //根据id查找学生
    @Test
    void selectStudentById() {

        Student student = studentMapper.selectById(10011001);
        System.out.println(student);
    }

    @Test
    void selectStudentPage(){

        int currentPage = 1,pageSize = 10;
        String studentName = null;
        Page<Student> page = new Page<>(currentPage, pageSize);

        //创建条件构造器
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.like(StringUtils.isNotEmpty(studentName), Student::getStudentName, studentName);
        //添加排序条件
        wrapper.orderByAsc(Student::getStudentId);

        studentMapper.selectPage(page, wrapper);

        log.info("page信息:current = {},pages = {},size = {},total = {},records = {}", page.getCurrent(), page.getPages(), page.getSize(), page.getTotal(), page.getRecords());

        System.out.println(page);
    }

    @Test
    void addStudent(){

        Student student = new Student();
        student.setStudentNumber(211001);
        student.setStudentName("AA");
        student.setStudentGender("1");
        student.setAccountName("AA");
        student.setAccountPassword("123456");
        student.setPhoneNumber("13589987656");

        int insert = studentMapper.insert(student);
        System.out.println(insert);
    }
}
