package pers.ervinse.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.ervinse.Dto.LoginUser;
import pers.ervinse.domain.Student;
import pers.ervinse.domain.Teacher;
import pers.ervinse.service.StudentService;
import pers.ervinse.service.TeacherService;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @PostMapping
    public R<String> login(@RequestBody LoginUser loginUser) {
        log.info("LoginController - login :loginUser = {}", loginUser);

        String loginType = loginUser.getLoginType();
        String loginAccountName = loginUser.getAccountName();
        String loginAccountPassword = loginUser.getAccountPassword();
        String passwordBySearch = "";
        if ("1".equals(loginType)) {
            Teacher loginTeacher = new Teacher();
            loginTeacher.setAccountName(loginAccountName);
            loginTeacher.setAccountPassword(loginAccountPassword);

            Teacher teacherBySearch = teacherService.selectTeacherByAccountName(loginTeacher);
            log.info("teacherBySearch = {}", teacherBySearch);

            if (teacherBySearch == null) {
                return R.getErrorInstance("账户未注册!");
            } else {
                passwordBySearch = teacherBySearch.getAccountPassword();
            }
        } else if ("2".equals(loginType)) {
            Student loginStudent = new Student();
            loginStudent.setAccountName(loginAccountName);
            loginStudent.setAccountPassword(loginAccountPassword);

            Student studentBySearch = studentService.selectStudentByAccountName(loginStudent);
            log.info("studentBySearch = {}", studentBySearch);

            if (studentBySearch == null) {
                return R.getErrorInstance("账户未注册!");
            } else {
                passwordBySearch = studentBySearch.getAccountPassword();
            }
        } else {
            throw new CustomException("未知错误");
        }

        if (loginAccountPassword.equals(passwordBySearch)) {
            return R.getSuccessInstance(null);
        } else {
            return R.getErrorInstance("账号或密码错误!");
        }

    }

}
