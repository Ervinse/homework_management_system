package pers.ervinse.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClaseCourse implements Serializable {
    private static final long serialVersionUID = 1L;

    //班级-课程id
    @TableId("id")
    private Long claseCourseId;
    //班级id
    private Long claseId;
    //课程id
    private Long courseId;
}
