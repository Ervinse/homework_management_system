package pers.ervinse.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    //文件id
    @TableId("id")
    private Long fileId;

    private String fileName;

    private String fileUserName;

    private Long referenceId;
}
