package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ervinse.Dto.HomeworkAnswerDto;
import pers.ervinse.common.CustomException;
import pers.ervinse.domain.File;
import pers.ervinse.domain.HomeworkAnswer;
import pers.ervinse.domain.Image;
import pers.ervinse.mapper.FileMapper;
import pers.ervinse.mapper.HomeworkAnswerMapper;
import pers.ervinse.mapper.ImageMapper;
import pers.ervinse.service.HomeworkAnswerService;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class HomeworkAnswerServiceImpl implements HomeworkAnswerService {

    @Autowired
    private HomeworkAnswerMapper homeworkAnswerMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private ImageMapper imageMapper;


    @Override
    public List<HomeworkAnswer> selectHomeworkAnswerList(Long homeworkId) {
        log.info("HomeworkAnswerService - selectHomeworkAnswerList :homeworkId = {}", homeworkId);


        LambdaQueryWrapper<HomeworkAnswer> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(homeworkId != null, HomeworkAnswer::getHomeworkId, homeworkId);
        //添加排序条件
        wrapper.orderByAsc(HomeworkAnswer::getHomeworkAnswerId);

        List<HomeworkAnswer> homeworkAnswerList = homeworkAnswerMapper.selectList(wrapper);

        return homeworkAnswerList;
    }

    @Override
    public HomeworkAnswer selectHomeworkAnswerById(Long homeworkAnswerId) {
        log.info("HomeworkAnswerService - selectHomeworkAnswerById :homeworkAnswerId = {}", homeworkAnswerId);

        return homeworkAnswerMapper.selectById(homeworkAnswerId);
    }

    @Override
    @Transactional
    public void addHomeworkAnswer(HomeworkAnswerDto homeworkAnswerDto) {
        log.info("HomeworkAnswerService - addHomeworkAnswer :homeworkAnswerDto = {}", homeworkAnswerDto);

        homeworkAnswerDto.setHomeworkRate(-1);
        homeworkAnswerMapper.insert(homeworkAnswerDto);

        List<String> imageUploadNameList = homeworkAnswerDto.getImageUploadNameList();
        if (imageUploadNameList.size() > 0) {
            Image imageToInsert = new Image();
            imageToInsert.setReferenceId(homeworkAnswerDto.getHomeworkAnswerId());
            imageUploadNameList.forEach(imageName -> {
                imageToInsert.setImageName(imageName);
                imageMapper.insert(imageToInsert);
            });
        }

        List<String> fileUploadNameList = homeworkAnswerDto.getFileUploadNameList();
        List<String> fileUploadUserNameList = homeworkAnswerDto.getFileUploadUserNameList();
        File[] fileArray = new File[fileUploadNameList.size()];
        if (fileUploadNameList.size() == fileUploadUserNameList.size()) {
            for (int i = 0; i < fileArray.length; i++) {
                File file = new File();
                file.setReferenceId(homeworkAnswerDto.getHomeworkAnswerId());
                file.setFileName(fileUploadNameList.get(i));
                file.setFileUserName(fileUploadUserNameList.get(i));
                fileArray[i] = file;
            }
        } else {
            throw new CustomException("服务器错误!");
        }

        System.out.println(Arrays.toString(fileArray));
        List<File> fileList = Arrays.asList(fileArray);
        fileList.forEach(file -> {
            fileMapper.insert(file);
        });

    }

}
