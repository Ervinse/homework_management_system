package pers.ervinse.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    //学生id
    @TableId("id")
    private Long studentId;
    //学生学号
    private String  studentNumber;
    //学生姓名
    private String studentName;
    //学生性别
    private String studentGender;
    //账号名
    private String accountName;
    //账号密码
    private String accountPassword;
    //账号头像
    private String accountPortrait;
    //手机号
    private String phoneNumber;

    //班级id
    private Long claseId;


}
