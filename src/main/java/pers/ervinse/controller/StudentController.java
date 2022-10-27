package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import pers.ervinse.common.R;
import pers.ervinse.domain.Clase;
import pers.ervinse.domain.Student;
import pers.ervinse.service.StudentService;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    /**
     * 根据条件获取学生分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 学生分页
     */
    @GetMapping("/page")
    public R<Page<Student>> getStudentPage(Integer currentPage, Integer pageSize, String searchValue) {
        log.info("StudentController - getStudentPage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Student> studentPage = studentService.selectStudentPage(currentPage, pageSize, searchValue);

        return R.getSuccessInstance(studentPage);
    }


    /**
     * 根据学生id获取学生数据详情
     *
     * @param studentId 学生id
     * @return 学生数据详情
     */
    @GetMapping
    public R<Student> getStudentById(Long studentId) {
        log.info("StudentController - getStudentById :studentId = {}", studentId);

        Student student = studentService.selectStudentById(studentId);
        if (student.getClaseId() == null) {
            student.setClaseId(0L);
        }

        return R.getSuccessInstance(student);
    }

    /**
     * 获取学生列表
     *
     * @return 学生列表
     */
    @GetMapping("/list")
    public R<List<Student>> getStudentList() {
        log.info("StudentController - getStudentList");

        List<Student> studentList = studentService.selectStudentList();

        return R.getSuccessInstance(studentList);
    }

    /**
     * 添加学生
     *
     * @param student 含有学生信息的对象
     * @return 添加结果
     */
    @Deprecated
    @PostMapping
    public R<String> addStudent(@RequestBody Student student) {
        log.info("StudentController - updateStudent :Student = {}", student);

        //对密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(student.getAccountPassword().getBytes(StandardCharsets.UTF_8));
        student.setAccountPassword(password);

        studentService.addStudent(student);
        return R.getSuccessInstance(null);
    }

    /**
     * 修改学生
     *
     * @param student 含有学生修改信息的对象
     * @return 修改结果
     */
    @PutMapping
    public R<String> updateStudent(@RequestBody Student student) {
        log.info("StudentController - updateStudent :Student = {}", student);

        //对密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(student.getAccountPassword().getBytes(StandardCharsets.UTF_8));
        student.setAccountPassword(password);

        studentService.updateStudentById(student);
        return R.getSuccessInstance(null);
    }

    /**
     * 根据id删除学生
     *
     * @param studentId 要删除的学生id
     * @return 删除结果
     */
    @DeleteMapping
    public R<String> deleteStudent(Long studentId) {
        log.info("StudentController - deleteStudent :studentId = {}", studentId);

        studentService.deleteStudentById(studentId);
        return R.getSuccessInstance(null);
    }

}
