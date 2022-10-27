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

import javax.servlet.http.HttpServletRequest;
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
     *
     * @param loginUser 含有登录信息的登录数据传输对象
     * @return 登录结果
     */
    @PostMapping
    public R<LoginUser> login(HttpServletRequest request, @RequestBody LoginUser loginUser) {
        log.info("LoginController - login :loginUser = {}", loginUser);

        //从数据传输对象中获取登录类型,登录账号和登录密码
        String loginType = loginUser.getLoginType();
        String loginAccountName = loginUser.getAccountName();
        String loginAccountPassword = loginUser.getAccountPassword();

        //对密码进行md5加密
        loginAccountPassword = DigestUtils.md5DigestAsHex(loginAccountPassword.getBytes(StandardCharsets.UTF_8));

        //教师登录
        if ("1".equals(loginType)) {
            Teacher loginTeacher = new Teacher();
            loginTeacher.setAccountName(loginAccountName);

            //从数据库中获取密码
            Teacher teacherBySearch = teacherService.selectTeacherByAccountName(loginTeacher);
            log.info("teacherBySearch = {}", teacherBySearch);

            if (teacherBySearch == null) {
                return R.getErrorInstance("账户未注册!");
            } else {
                if (loginAccountPassword.equals(teacherBySearch.getAccountPassword())) {
                    log.info("登录成功");

                    //处理返回数值,抹去密码
                    loginUser.setAccountPassword("");
                    loginUser.setId(teacherBySearch.getTeacherId());
                    loginUser.setUserName(teacherBySearch.getTeacherName());
                    loginUser.setAccountPortrait(teacherBySearch.getAccountPortrait());
                    loginUser.setAccountType(teacherBySearch.getAccountType());

                    //存入session
                    request.getSession().setAttribute("user", loginUser);
                    return R.getSuccessInstance(loginUser);
                } else {
                    return R.getErrorInstance("账号或密码错误!");
                }
            }

            //学生登录
        } else if ("2".equals(loginType)) {
            Student loginStudent = new Student();
            loginStudent.setAccountName(loginAccountName);

            Student studentBySearch = studentService.selectStudentByAccountName(loginStudent);
            log.info("studentBySearch = {}", studentBySearch);

            if (studentBySearch == null) {
                return R.getErrorInstance("账户未注册!");
            } else {
                if (loginAccountPassword.equals(studentBySearch.getAccountPassword())) {
                    log.info("登录成功");

                    //处理返回数值,抹去密码
                    loginUser.setAccountPassword("");
                    loginUser.setId(studentBySearch.getStudentId());
                    loginUser.setUserName(studentBySearch.getStudentName());
                    loginUser.setAccountPortrait(studentBySearch.getAccountPortrait());
                    loginUser.setAccountType("3");

                    //存入session
                    request.getSession().setAttribute("user", loginUser);
                    return R.getSuccessInstance(loginUser);
                } else {
                    return R.getErrorInstance("账号或密码错误!");
                }
            }
        } else {
            throw new CustomException("未知错误");
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
        return R.getSuccessOperationInstance();
    }

    /**
     * 登出
     * @param request 请求
     * @return 登出请求响应
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return R.getSuccessOperationInstance();
    }

}
