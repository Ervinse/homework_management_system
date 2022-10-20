package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.ervinse.common.R;
import pers.ervinse.domain.Student;
import pers.ervinse.service.StudentService;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/page")
    public R<Page<Student>> getStudentPage(Integer currentPage, Integer pageSize, String studentName){
        log.info("StudentController - getStudentPage :currentPage = {},pageSize = {},name = {}", currentPage, pageSize, studentName);

        Page<Student> studentPage = studentService.selectStudentPage(currentPage, pageSize, studentName);

        return R.getSuccessInstance(studentPage);
    }
}
