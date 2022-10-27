package pers.ervinse.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Clase implements Serializable {

    private static final long serialVersionUID = 1L;

    //班级id
    @TableId("id")
    private Long claseId;
    //班级名
    private String claseName;
    //入学时间
    private int timeOfEnrollment;
    //班主任
    private Long claseTeacherId;
    //班长
    private Long claseLeaderId;
}
