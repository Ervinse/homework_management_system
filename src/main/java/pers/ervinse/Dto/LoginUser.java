package pers.ervinse.Dto;

import lombok.Data;

@Data
public class LoginUser {

    //账号名
    private String accountName;
    //账号密码
    private String accountPassword;
    //登录类型("1"为教师,"2"为学生)
    private String loginType;

    //电话号码
    private String phoneNumber;

    //用户id
    private Long id;
    //姓名
    private String userName;
    //用户头像
    private String accountPortrait;
    //账户类型("1"为管理员,"2"为用户,"3"为学生)
    private String accountType;


}
