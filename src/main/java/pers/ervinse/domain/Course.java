package pers.ervinse.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    //课程id
    @TableId("id")
    private Long courseId;

    //课程名
    private String courseName;

    //上课地址
    private String courseAddress;

    //课程描述
    private String courseDescription;

    //任课老师id
    private Long courseTeacherId;
}
