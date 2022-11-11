package pers.ervinse.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pers.ervinse.exception.BusinessException;
import pers.ervinse.exception.ProgramException;
import pers.ervinse.service.CommonService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {


    //图片存放目录
    @Value("${image-path}")
    private String imageDirectoryPath;

    //文件存放目录
    @Value("${file-path}")
    private String fileDirectoryPath;


    /**
     * 接收上传图片,并将图片转存到对应的路径中
     *
     * @param file 上传的图片
     * @return 图片新文件名
     */
    @Override
    public String uploadImage(MultipartFile file) {

        //将图片转存到服务器中
        return saveFile(file, imageDirectoryPath);
    }

    /**
     * 接收上传文件,并将图片转存到对应的路径中
     * @param file 上传的文件
     * @return 文件新文件名
     */
    @Override
    public String uploadFile(MultipartFile file){

        //将文件转存到服务器中
        return saveFile(file, fileDirectoryPath);
    }



    /**
     * 根据图片文件名删除图片
     * @param imageName 图片文件名
     */
    @Override
    public void deleteImage(String imageName) {

        if (imageName == null){
            throw new ProgramException("服务器错误,图片删除异常");
        }
        File imageFile = new File(imageDirectoryPath + imageName);

        if (!imageFile.delete()){
            throw new ProgramException("服务器错误,图片删除异常");
        }
    }

    /**
     * 根据文件文件名删除文件
     * @param fileName 文件文件名
     */
    @Override
    public void deleteFile(String fileName) {

        if (fileName == null){
            throw new ProgramException("服务器错误,文件删除异常");
        }
        File imageFile = new File(fileDirectoryPath + fileName);

        if (!imageFile.delete()){
            throw new ProgramException("服务器错误,文件删除异常");
        }
    }



    /**
     * 将文件转存到指定地址
     * @param file 要转存的文件
     * @param path 文件转存路径
     * @return 保存到服务器后的文件名
     */
    private String saveFile(MultipartFile file,String path){
        log.info("CommonController - saveFile : file = {},path = {}", file.toString(),path);

        //通过上传文件的原始文件名获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID重新生成文件名
        String fileName = UUID.randomUUID() + suffix;

        //根据配置文件获取图片存放目录,并生成对应路径
        File imageDirectoryPathFile = new File(path);

        //根据传入的存放目录的路径创建文件夹
        if (!imageDirectoryPathFile.exists()) {
            imageDirectoryPathFile.mkdir();
        }

        //根据目录路径和文件名,将文件转存到文件存放路径
        File filePath = new File(path + fileName);
        log.info("filePath = {}", filePath);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }
}
