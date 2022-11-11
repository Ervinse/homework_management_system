package pers.ervinse.common;

import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import pers.ervinse.Dto.LoginUser;
import pers.ervinse.domain.Student;
import pers.ervinse.domain.Teacher;
import pers.ervinse.exception.BusinessException;
import pers.ervinse.exception.ProgramException;
import pers.ervinse.service.StudentService;
import pers.ervinse.service.TeacherService;
import pers.ervinse.utils.SMSUtils;
import pers.ervinse.utils.ValidateCodeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    //是否启用短信服务
    @Value("${SMS.enable}")
    private boolean SMSUtilsEnable;

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
            throw new ProgramException("未知错误");
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
     * 检查账户和手机号是否匹配
     *
     * @param loginUser 含有账户名和手机号的登录用户类
     * @return 检查结果响应
     */
    @PostMapping("/checkPhoneNumber")
    public R<String> checkPhoneNumber(@RequestBody LoginUser loginUser) {
        log.info("LoginController - checkPhoneNumber :loginUser = {}", loginUser);

        //从前端获取账户名和手机号码
        String accountName = loginUser.getAccountName();
        String phoneNumber = loginUser.getPhoneNumber();

        //根据账户名查询教师
        Teacher teacher = new Teacher();
        teacher.setAccountName(accountName);
        List<Teacher> teacherList = teacherService.selectTeacherByConditionInOr(teacher);
        //查询到有相关账户,且手机号码正确,返回账户确认响应
        if (teacherList.size() > 0) {
            if (StringUtils.equals(phoneNumber, teacherList.get(0).getPhoneNumber())) {
                return R.getSuccessOperationInstance();
            }
        }

        //根据账户名查询学生
        Student student = new Student();
        student.setAccountName(accountName);
        List<Student> studentList = studentService.selectStudentListByConditionInOr(student);
        //查询到有相关账户,且手机号码正确,返回账户确认响应
        if (studentList.size() > 0) {
            if (StringUtils.equals(phoneNumber, studentList.get(0).getPhoneNumber())) {
                return R.getSuccessOperationInstance();
            }
        }

        //没有查询到教师或学生相关账户,或查询到后手机号码不匹配
        return R.getErrorInstance("手机号和账户不匹配");
    }


    @PutMapping("/resetPassword")
    public R<String> resetPassword(@RequestBody LoginUser loginUser, HttpSession session) {
        log.info("LoginController - resetPassword :loginUser = {}", loginUser);

        String accountName = loginUser.getAccountName();
        String loginType = loginUser.getLoginType();
        String code = loginUser.getCode();

        //从session中获取验证码
        String codeBySession = (String) session.getAttribute(accountName);
        log.info("codeBySession = {}", codeBySession);
        if (codeBySession != null) {
            //判断验证码是否正确
            if (codeBySession.equals(code)) {
                //教师重置
                if ("1".equals(loginType)) {
                    teacherService.resetPassword(accountName);
                    //学生重置
                } else if ("2".equals(loginType)) {
                    studentService.resetPassword(accountName);
                } else {
                    throw new ProgramException();
                }
            } else {
                return R.getErrorInstance("验证码错误!");
            }
        } else {
            return R.getErrorInstance("验证码超时!");
        }
        return R.getSuccessOperationInstance();
    }


    /**
     * 发送手机验证码
     *
     * @param accountName 需要重置密码的账户名
     * @param phoneNumber 发送短信的手机号码
     * @param session     session
     * @return 手机验证码响应
     */
    @GetMapping("/sendMsg")
    public R<String> sendMsg(String accountName, String phoneNumber, HttpSession session) throws ClientException {
        log.info("UserController - sendMsg :phoneNumber = {}", phoneNumber);

        //生成四位随机验证码
        String code = ValidateCodeUtils.generateValidateCode(4).toString();

        //如果开启短信服务,发送短信验证码
        if (SMSUtilsEnable) {
            log.info("短信服务已启用");
            SMSUtils.sendMessage(phoneNumber, code);
        }

        log.info("短信验证码:{}", code);

        //将账户名和验证码保存到session中
        session.setAttribute(accountName, code);

//            //将生成的验证码缓存到redis中,并设置有效期为五分钟
//            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

        return R.getSuccessInstance("手机验证码发送成功");
    }


    /**
     * 登出
     *
     * @param request 请求
     * @return 登出请求响应
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return R.getSuccessOperationInstance();
    }

}
