package pers.ervinse.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 作业答案字段填充处理器
 */
@Component
@Slf4j
public class HomeworkAnswerMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        log.info("信息插入填充 - metaObject : {}",metaObject.getOriginalObject());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        log.info("信息修改填充 - metaObject : {}",metaObject.getOriginalObject());
    }
}
