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

    /**
     * 根据条件获取学生分页
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @param searchValue 搜索值
     * @return 学生分页
     */
    @GetMapping("/page")
    public R<Page<Student>> getStudentPage(Integer currentPage, Integer pageSize, String searchValue){
        log.info("StudentController - getStudentPage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Student> studentPage = studentService.selectStudentPage(currentPage, pageSize, searchValue);

        return R.getSuccessInstance(studentPage);
    }


    /**
     * 根据学生id获取学生数据详情
     * @param studentId 学生id
     * @return 学生数据详情
     */
    @GetMapping
    public R<Student> getStudentById(String studentId){
        log.info("StudentController - getStudentById :studentId = {}", studentId);

        Student student = studentService.selectStudentById(studentId);
        if (student.getClaseId() == null){
            student.setClaseId(0L);
        }

        return R.getSuccessInstance(student);
    }
}
