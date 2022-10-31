package pers.ervinse.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    //学生id
    @TableId("id")
    private Long imageId;
    private String imageName;
    private Long referenceId;
}
