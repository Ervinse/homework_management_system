package pers.ervinse.Dto;

import lombok.Data;

@Data
public class LoginUser {

    //账号名
    private String accountName;
    //账号密码
    private String accountPassword;
    //账号类型("1"为教师,"2"为学生)
    private String loginType;
}
