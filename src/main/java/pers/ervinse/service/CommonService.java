package pers.ervinse.service;

import org.springframework.web.multipart.MultipartFile;
import pers.ervinse.common.R;

public interface CommonService {
    String uploadImage(MultipartFile file);

    String uploadFile(MultipartFile file);

    void deleteImage(String imageName);

    void deleteFile(String fileName);
}
