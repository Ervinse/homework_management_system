package pers.ervinse.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.ervinse.mapper.SimulationMapper;
import pers.ervinse.service.CommonService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    //图片存放目录
    @Value("${image-path}")
    private String imageDirectoryPath;

    //文件存放目录
    @Value("${file-path}")
    private String fileDirectoryPath;


    @Autowired
    private CommonService commonService;

    @Autowired
    private SimulationMapper simulationMapper;

    /**
     * 接收上传图片,并将图片转存到对应的路径中
     *
     * @param file 上传的图片
     * @return 含有图片新文件名的响应
     */
    @PostMapping("/uploadImage")
    public R<String> uploadImage(MultipartFile file) {
        log.info("CommonController - uploadImage : file = {}", file.toString());

        String fileName = commonService.uploadImage(file);
        return R.getSuccessInstance(fileName);
    }

    /**
     * 接收上传文件,并将图片转存到对应的路径中
     *
     * @param file 上传的文件
     * @return 含有文件新文件名的响应
     */
    @PostMapping("/uploadFile")
    public R<String> uploadFile(MultipartFile file) {
        log.info("CommonController - uploadFile : file = {}", file.toString());

        //将文件转存到服务器中
        String fileName = commonService.uploadFile(file);
        return R.getSuccessInstance(fileName);
    }


    /**
     * 根据图片名下载图片
     *
     * @param imageName 图片文件名
     * @param response  响应
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
     * 根据文件名下载文件
     *
     * @param fileName 文件名
     * @param response 响应
     */
    @GetMapping("/downloadFile")
    public void downloadFile(String fileName, HttpServletResponse response) throws RuntimeException {
        log.info("CommonController - downloadFile : fileName = {}", fileName);

        FileInputStream fileInputStream = null;
        ServletOutputStream servletOutputStream = null;

        try {
            fileInputStream = new FileInputStream(fileDirectoryPath + fileName);

            servletOutputStream = response.getOutputStream();

            //设置响应数据流类型为二进制流(即不知道下载文件类型)
            response.setContentType("application/octet-stream");

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
     *
     * @param imageName 图片文件名
     * @return 删除图片响应
     */
    @DeleteMapping("/deleteImage")
    public R<String> deleteImage(String imageName) {
        log.info("CommonController - deleteImage : imageName = {}", imageName);

        boolean success = commonService.deleteImage(imageName);
        if (success) {
            return R.getSuccessOperationInstance();
        } else {
            log.error("无法找到图片，删除图片失败，跳过此步骤");
            return R.getErrorInstance("无法找到图片");
        }
    }

    /**
     * 根据文件文件名删除文件
     *
     * @param fileName 文件文件名
     * @return 删除文件响应
     */
    @DeleteMapping("/deleteFile")
    public R<String> deleteFile(String fileName) {
        log.info("CommonController - deleteFile : fileName = {}", fileName);

        boolean success = commonService.deleteFile(fileName);
        if (success) {
            return R.getSuccessOperationInstance();
        } else {
            log.error("无法找到文件，删除文件失败，跳过此步骤");
            return R.getErrorInstance("无法找到文件");
        }
    }


    /**
     * 生成全部模拟数据(包含作业,作业答案以及相关文件,图片数据)
     *
     * @return 生成模拟数据响应
     */
    @PostMapping("/fullSimulatedData")
    public R<String> generateFullSimulatedData() {
        log.info("CommonController - generateFullSimulatedData");

        simulationMapper.generateFullSimulatedData();

        return R.getSuccessOperationInstance();
    }

    /**
     * 生成部分模拟数据(仅包含教师,学生,班级,课程数据,不包含作业,作业答案以及相关文件,图片数据)
     *
     * @return 生成模拟数据响应
     */
    @PostMapping("/basicSimulatedData")
    public R<String> generateBasicSimulatedData() {
        log.info("CommonController - generateBasicSimulatedData");

        simulationMapper.generateBasicSimulatedData();

        return R.getSuccessOperationInstance();
    }

}
