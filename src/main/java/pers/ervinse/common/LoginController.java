package pers.ervinse.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import pers.ervinse.Dto.LoginUser;
import pers.ervinse.domain.Student;
import pers.ervinse.domain.Teacher;
import pers.ervinse.service.StudentService;
import pers.ervinse.service.TeacherService;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    /**
     * 登录
     * @param loginUser 含有登录信息的登录数据传输对象
     * @return 登录结果
     */
    @PostMapping
    public R<String> login(@RequestBody LoginUser loginUser) {
        log.info("LoginController - login :loginUser = {}", loginUser);

        //从数据传输对象中获取登录类型,登录账号和登录密码
        String loginType = loginUser.getLoginType();
        String loginAccountName = loginUser.getAccountName();
        String loginAccountPassword = loginUser.getAccountPassword();

        //对密码进行md5加密
        loginAccountPassword = DigestUtils.md5DigestAsHex(loginAccountPassword.getBytes(StandardCharsets.UTF_8));

        //用于存放从数据库中获取的密码
        String passwordBySearch;

        //教师登录
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

        //学生登录
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

        //判断密码是否正确
        if (loginAccountPassword.equals(passwordBySearch)) {
            return R.getSuccessInstance(null);
        } else {
            return R.getErrorInstance("账号或密码错误!");
        }

    }

    /**
     * 学生注册
     *
     * @param student 含有学生信息的对象
     * @return 添加结果
     */
    @PostMapping("/register")
    public R<String> registerStudent(@RequestBody Student student) {
        log.info("LoginController - registerStudent :Student = {}", student);

        //对密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(student.getAccountPassword().getBytes(StandardCharsets.UTF_8));
        student.setAccountPassword(password);

        studentService.addStudent(student);
        return R.getSuccessInstance(null);
    }

}
