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
     * 根据图片名删除图片
     * 如果由于图片名无效而删除失败，直接抛出异常，回滚操作
     * 如果由于io异常（无法读取文件等），跳过删除步骤，继续执行操作
     * @param imageName 要删除的图片名
     * @return 是否成功删除
     */
    @Override
    public boolean deleteImage(String imageName) {

        if (imageName == null){
            throw new ProgramException("服务器错误,图片删除异常");
        }
        File imageFile = new File(imageDirectoryPath + imageName);

        return imageFile.delete();
    }

    /**
     * 根据文件文件名删除文件
     * @param fileName 要删除的文件名
     * 如果由于文件名无效而删除失败，直接抛出异常，回滚操作
     * 如果由于io异常（无法读取文件等），跳过删除步骤，继续执行操作
     * @return 是否成功删除
     */
    @Override
    public boolean deleteFile(String fileName) {

        if (fileName == null){
            throw new ProgramException("服务器错误,文件删除异常");
        }
        File file = new File(fileDirectoryPath + fileName);

        return file.delete();
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
