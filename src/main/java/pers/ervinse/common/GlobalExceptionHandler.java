package pers.ervinse.common;

import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.ervinse.exception.BusinessException;
import pers.ervinse.exception.ProgramException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class, Controller.class, Service.class})   //要增强的类
public class GlobalExceptionHandler {

    /**
     * 处理sql完整性约束相关异常
     *
     * @param exception 异常对象
     * @return 抛异常时的响应对象
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)   //处理的异常类
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        String exceptionMessage = exception.getMessage();
        log.info("GlobalExceptionHandler - SQLIntegrityConstraintViolationException : {}", exceptionMessage);

        if (exceptionMessage.contains("Duplicate entry")) {    //判断唯一约束相关异常

            //以空格为界,截取错误信息中第6个单词,作为错误关键字
            String[] exceptionMessageList = exceptionMessage.split(" ");

            String returnMessage = generateDuplicateReturnMessage(exceptionMessageList);

            return R.getErrorInstance(returnMessage);
        } else {
            return R.getErrorInstance("数据库未知错误");
        }
    }

    /**
     * 判断错误信息中的关键字,确定重名字段
     *
     * @param exceptionMessageList 错误信息
     * @return 返回前台信息
     */
    private String generateDuplicateReturnMessage(String[] exceptionMessageList) {

        if ("'student.student_number'".equals(exceptionMessageList[5])) {
            return "添加失败,学号不能相同!";
        } else if ("'student.account_name'".equals(exceptionMessageList[5]) || "'teacher.account_name'".equals(exceptionMessageList[5])) {
            return "添加失败,登录账号名不能相同!";
        } else if ("'course.course_name'".equals(exceptionMessageList[5])) {
            return "添加失败,课程名不能相同!";
        } else if ("'clase.clase_name'".equals(exceptionMessageList[5])) {
            return "添加失败,班级名不能相同!";
        } else if ("'homework.homework_name'".equals(exceptionMessageList[5])) {
            return "添加失败,作业名不能相同!";
        } else {
            return "添加失败,未知字段重复!";
        }

    }

    /**
     * 处理sql相关异常
     *
     * @param exception 异常对象
     * @return 抛异常时的响应对象
     */
    @ExceptionHandler(SQLException.class)   //处理的异常类
    public R<String> exceptionHandler(SQLException exception) {
        String exceptionMessage = exception.getMessage();
        log.error("GlobalExceptionHandler - SQLException : {}", exceptionMessage);

        if (exceptionMessage.contains("Access denied")) {    //判断唯一约束相关异常
            log.error("服务器错误:数据库拒绝访问");
            return R.getErrorInstance("服务器错误:数据库拒绝访问");
        } else if (exceptionMessage.contains("Communications link failure")) {
            log.error("服务器错误:数据库连接失败");
            return R.getErrorInstance("服务器错误:数据库连接失败");
        } else {
            return R.getErrorInstance("未知错误");
        }
    }

    /**
     * 处理业务相关异常
     *
     * @param exception 异常对象
     * @return 抛异常时的响应对象
     */
    @ExceptionHandler(BusinessException.class)   //处理的异常类
    public R<String> exceptionHandler(BusinessException exception) {
        String exceptionMessage = exception.getMessage();
        log.info("GlobalExceptionHandler - BusinessException : {}", exceptionMessage);

        return R.getErrorInstance(exceptionMessage);
    }


    /**
     * 处理程序相关异常
     *
     * @param exception 异常对象
     * @return 抛异常时的响应对象
     */
    @ExceptionHandler(ProgramException.class)   //处理的异常类
    public R<String> exceptionHandler(ProgramException exception) {
        String exceptionMessage = exception.getMessage();
        log.error("GlobalExceptionHandler - ProgramException : {}", exceptionMessage);

        return R.getErrorInstance(exceptionMessage);
    }

    /**
     * 阿里云短信异常
     *
     * @param exception 异常对象
     * @return 抛异常时的响应对象
     */
    @ExceptionHandler(ClientException.class)   //处理的异常类
    public R<String> exceptionHandler(ClientException exception) {
        String exceptionMessage = exception.getMessage();
        log.error("GlobalExceptionHandler - ClientException : {}", exceptionMessage);

        return R.getErrorInstance("服务器错误:短信服务异常");
    }


}
