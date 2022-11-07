package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.domain.File;
import pers.ervinse.mapper.FileMapper;
import pers.ervinse.service.FileService;

import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    /**
     * 根据条件查询文件列表
     * @param file 查询的文件条件
     * @return 查询到的文件列表
     */
    @Override
    public List<File> selectFileListByConditionInOr(File file) {
        log.info("FileService - selectFileListByConditionInOr : File = {}", file);

        //创建条件构造器
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.eq(file.getFileId() != null, File::getFileId, file.getFileId())
                .or()
                .eq(StringUtils.isNotEmpty(file.getFileName()), File::getFileName, file.getFileName())
                .or()
                .eq(StringUtils.isNotEmpty(file.getFileUserName()), File::getFileUserName, file.getFileUserName())
                .or()
                .eq(file.getReferenceId() != null, File::getReferenceId, file.getReferenceId());

        return fileMapper.selectList(wrapper);
    }

    /**
     * 根据参考id删除文件
     * @param referenceId 参考id
     */
    @Override
    public void deleteFileByReferenceId(Long referenceId) {
        log.info("FileService - deleteFileByReferenceId :referenceId = {}", referenceId);

        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getReferenceId, referenceId);
        fileMapper.delete(wrapper);
    }
}
