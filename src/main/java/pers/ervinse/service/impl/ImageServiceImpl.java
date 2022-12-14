package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.domain.Image;
import pers.ervinse.mapper.ImageMapper;
import pers.ervinse.service.CommonService;
import pers.ervinse.service.ImageService;

import java.util.List;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private CommonService commonService;

    /**
     * 根据条件查询图片列表
     * @param image 查询的图片条件
     * @return 查询到的图片列表
     */
    @Override
    public List<Image> selectImageListByConditionInOr(Image image) {
        log.info("ImageService - selectImageListByConditionInOr : image = {}", image);

        //创建条件构造器
        LambdaQueryWrapper<Image> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.eq(image.getImageId() != null, Image::getImageId, image.getImageId())
                .or()
                .eq(StringUtils.isNotEmpty(image.getImageName()), Image::getImageName, image.getImageName())
                .or()
                .eq(image.getReferenceId() != null, Image::getReferenceId, image.getReferenceId());

        return imageMapper.selectList(wrapper);
    }

    /**
     * 根据参考id删除图片
     * @param referenceId 参考id
     */
    @Override
    public void deleteImageByReferenceId(Long referenceId) {
        log.info("ImageService - deleteImageByReferenceId :referenceId = {}", referenceId);


        //根据参考答案获取图片列表,根据图片列表中的图片名依次删除服务器上的图片
        Image imageToSelect = new Image();
        imageToSelect.setReferenceId(referenceId);
        List<Image> imageList = selectImageListByConditionInOr(imageToSelect);
        if (imageList.size() > 0) {
            imageList.forEach(image -> commonService.deleteImage(image.getImageName()));
        }

        LambdaQueryWrapper<Image> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Image::getReferenceId, referenceId);
        imageMapper.delete(wrapper);
    }

}
