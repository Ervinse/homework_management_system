package pers.ervinse.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


    /**
     * 接收上传图片,并将图片转存到对应的路径中
     * @param file 上传的图片
     * @return 含有图片新文件名的响应
     */
    @PostMapping("/upload")
    public R<String> uploadImage(MultipartFile file){
        log.info("CommonController - upload : file = {}", file.toString());

        //通过上传图片的原始文件名获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID重新生成文件名
        String fileName = UUID.randomUUID() + suffix;

        //根据配置文件获取图片存放目录,并生成对应路径
        log.info("CommonController - upload : imageDirectoryPath = {}", imageDirectoryPath);
        File imageDirectoryPathFile = new File(imageDirectoryPath);

        //根据图片存放目录的路径创建文件夹
        if (!imageDirectoryPathFile.exists()){
            imageDirectoryPathFile.mkdir();
        }

        //根据目录路径和图片文件名,将图片转存到图片存放路径
        File filePath = new File(imageDirectoryPath + fileName);
        log.info("CommonController - upload : filePath = {}", filePath);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.getSuccessInstance(fileName);
    }


    /**
     * 根据图片存放目录下载图片
     * @param name 图片文件名
     * @param response 响应
     */
    @GetMapping("/download")
    public void downloadImage(String name, HttpServletResponse response){

        FileInputStream fileInputStream = null;
        ServletOutputStream servletOutputStream = null;

        try {
            fileInputStream = new FileInputStream(imageDirectoryPath + name);

            servletOutputStream = response.getOutputStream();

            //通过图片文件名获取文件后缀
            String suffix = name.substring(name.lastIndexOf("."));
            response.setContentType("image/" + suffix.substring(1));

            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                servletOutputStream.write(bytes,0,len);
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
}
