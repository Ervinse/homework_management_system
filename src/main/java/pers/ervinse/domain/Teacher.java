package pers.ervinse.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    //学生id
    @TableId("id")
    private Long teacherId;
    //教师姓名
    private String teacherName;
    //教师性别
    private String teacherGender;
    //账号名
    private String accountName;
    //账号密码
    private String accountPassword;
    //账号类型
    private String accountType;
    //账号状态
    private String accountStatus;
    //账号头像
    private String accountPortrait;
    //手机号
    private String phoneNumber;


}
