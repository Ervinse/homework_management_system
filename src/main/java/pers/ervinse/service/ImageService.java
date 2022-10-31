package pers.ervinse.service;

import pers.ervinse.domain.Image;

import java.util.List;

public interface ImageService {
    List<Image> selectImageListByConditionInOr(Image image);
}
