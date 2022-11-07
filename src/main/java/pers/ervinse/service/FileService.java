package pers.ervinse.service;

import pers.ervinse.domain.File;

import java.util.List;

public interface FileService {
    List<File> selectFileListByConditionInOr(File file);

    void deleteFileByReferenceId(Long referenceId);
}
