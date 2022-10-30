package pers.ervinse.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Homework implements Serializable {

    private static final long serialVersionUID = 1L;

    //作业id
    @TableId("id")
    private Long homeworkId;
    //作业名
    private String homeworkName;
    //作业描述
    private String homeworkDescription;
    //班级课程id
    private String classCourseId;
}
