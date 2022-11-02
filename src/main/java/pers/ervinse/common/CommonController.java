package pers.ervinse.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    //图片存放目录
    @Value("${image-path}")
    private String imageDirectoryPath;

    @Value("${file-path}")
    private String fileDirectoryPath;


    /**
     * 接收上传图片,并将图片转存到对应的路径中
     *
     * @param file 上传的图片
     * @return 含有图片新文件名的响应
     */
    @PostMapping("/uploadImage")
    public R<String> uploadImage(MultipartFile file) {
        log.info("CommonController - uploadImage : file = {}", file.toString());

        //将图片转存到服务器中
        String fileName = saveFile(file, imageDirectoryPath);
        return R.getSuccessInstance(fileName);
    }

    @PostMapping("/uploadFile")
    public R<String> uploadFile(MultipartFile file){
        log.info("CommonController - uploadFile : file = {}", file.toString());

        //将图片转存到服务器中
        String fileName = saveFile(file, fileDirectoryPath);
        return R.getSuccessInstance(fileName);
    }


    /**
     * 根据图片名下载图片
     *
     * @param imageName     图片文件名
     * @param response 响应
     */
    @GetMapping("/downloadImage")
    public void downloadImage(String imageName, HttpServletResponse response) throws RuntimeException {
        log.info("CommonController - downloadImage : imageName = {}", imageName);

        FileInputStream fileInputStream = null;
        ServletOutputStream servletOutputStream = null;

        try {
            fileInputStream = new FileInputStream(imageDirectoryPath + imageName);

            servletOutputStream = response.getOutputStream();

            //通过图片文件名获取文件后缀
            String suffix = imageName.substring(imageName.lastIndexOf("."));
            response.setContentType("image/" + suffix.substring(1));

            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                servletOutputStream.write(bytes, 0, len);
                servletOutputStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (servletOutputStream != null) {
                try {
                    servletOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


    /**
     * 根据图片文件名删除图片
     * @param imageName 图片文件名
     * @return 删除图片响应
     */
    @DeleteMapping("/deleteImage")
    public R<String> deleteImage(String imageName) {
        log.info("CommonController - deleteImage : imageName = {}", imageName);

        if (imageName == null){
            throw new CustomException("服务器错误,图片删除异常");
        }
        File imageFile = new File(imageDirectoryPath + imageName);

        if (imageFile.delete()){
            return R.getSuccessOperationInstance();
        }else {
            throw new CustomException("服务器错误,图片删除异常");
        }
    }

    /**
     * 将文件转存到指定地址
     * @param file 要转存的文件
     * @param path 文件转存路径
     * @return 保存到服务器后的文件名
     */
    public String saveFile(MultipartFile file,String path){
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
