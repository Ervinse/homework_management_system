package pers.ervinse.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class HomeworkAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    //作业答案id
    @TableId("id")
    private Long homeworkAnswerId;

    private String homeworkAnswerContent;

    private int homeworkRate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Long homeworkId;

    private Long studentId;
}
