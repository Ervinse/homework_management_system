package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ervinse.Dto.HomeworkAnswerDto;
import pers.ervinse.exception.BusinessException;
import pers.ervinse.domain.File;
import pers.ervinse.domain.HomeworkAnswer;
import pers.ervinse.domain.Image;
import pers.ervinse.exception.ProgramException;
import pers.ervinse.mapper.FileMapper;
import pers.ervinse.mapper.HomeworkAnswerMapper;
import pers.ervinse.mapper.ImageMapper;
import pers.ervinse.service.CommonService;
import pers.ervinse.service.FileService;
import pers.ervinse.service.HomeworkAnswerService;
import pers.ervinse.service.ImageService;

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

    @Autowired
    private ImageService imageService;

    @Autowired
    private FileService fileService;

    @Autowired
    private CommonService commonService;


    /**
     * 根据作业id获取作业答案列表
     *
     * @param homeworkId 作业答案id
     * @return 作业答案列表
     */
    @Override
    public List<HomeworkAnswer> selectHomeworkAnswerList(Long homeworkId) {
        log.info("HomeworkAnswerService - selectHomeworkAnswerList :homeworkId = {}", homeworkId);


        LambdaQueryWrapper<HomeworkAnswer> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(homeworkId != null, HomeworkAnswer::getHomeworkId, homeworkId);
        //添加排序条件
        wrapper.orderByAsc(HomeworkAnswer::getHomeworkAnswerId);

        return homeworkAnswerMapper.selectList(wrapper);
    }

    /**
     * 根据id获取作业答案
     *
     * @param homeworkAnswerId 作业答案id
     * @return 作业答案
     */
    @Override
    public HomeworkAnswer selectHomeworkAnswerById(Long homeworkAnswerId) {
        log.info("HomeworkAnswerService - selectHomeworkAnswerById :homeworkAnswerId = {}", homeworkAnswerId);

        return homeworkAnswerMapper.selectById(homeworkAnswerId);
    }


    /**
     * 根据条件查询作业答案列表
     *
     * @param homeworkAnswer 查询的作业答案条件
     * @return 查询到的作业答案列表
     */
    @Override
    public List<HomeworkAnswer> selectHomeworkAnswerListByConditionInAnd(HomeworkAnswer homeworkAnswer) {
        log.info("HomeworkAnswerService - selectHomeworkAnswerListByConditionInAnd :homeworkAnswer = {}", homeworkAnswer);

        //创建条件构造器
        LambdaQueryWrapper<HomeworkAnswer> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.eq(homeworkAnswer.getHomeworkAnswerId() != null, HomeworkAnswer::getHomeworkAnswerId, homeworkAnswer.getHomeworkAnswerId())
                .eq(homeworkAnswer.getHomeworkId() != null, HomeworkAnswer::getHomeworkId, homeworkAnswer.getHomeworkId())
                .eq(homeworkAnswer.getStudentId() != null, HomeworkAnswer::getStudentId, homeworkAnswer.getStudentId())
                .eq(homeworkAnswer.getHomeworkRate() != 0, HomeworkAnswer::getHomeworkRate, homeworkAnswer.getHomeworkRate());
        return homeworkAnswerMapper.selectList(wrapper);
    }

    /**
     * 根据作业id和学生id获取作业答案评分
     *
     * @param homeworkId 作业id
     * @param studentId 学生id
     * @return 作业答案评分
     */
    @Override
    public int selectHomeworkAnswerRateByStudentAndHomework(Long homeworkId, Long studentId) {
        log.info("HomeworkAnswerService - selectHomeworkRateByStudentAndHomework :homeworkAnswerId = {} , studentId = {}", homeworkId, studentId);

        //创建条件构造器
        LambdaQueryWrapper<HomeworkAnswer> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.eq(HomeworkAnswer::getHomeworkId, homeworkId)
                .eq(HomeworkAnswer::getStudentId, studentId);

        List<HomeworkAnswer> homeworkAnswerList = homeworkAnswerMapper.selectList(wrapper);
        //查询到提交记录,即作业答案已提交状态
        //评分大于等于0为正常评分,-1为未评分,-2为未提交作业
        if (homeworkAnswerList.size() > 0) {
            return homeworkAnswerList.get(0).getHomeworkRate();
        } else {    //没有查询到提交记录,即作业未提交
            return -2;
        }
    }


    /**
     * 添加作业答案
     *
     * @param homeworkAnswerDto 含有所属作业信息和提交相关信息的作业答案传输对象
     */
    @Override
    @Transactional
    public void addHomeworkAnswer(HomeworkAnswerDto homeworkAnswerDto) {
        log.info("HomeworkAnswerService - addHomeworkAnswer :homeworkAnswerDto = {}", homeworkAnswerDto);

        //判断是否为新增回答
        boolean isNewAnswer = true;

        HomeworkAnswer homeworkAnswer = new HomeworkAnswer();
        homeworkAnswer.setHomeworkId(homeworkAnswerDto.getHomeworkId());
        homeworkAnswer.setStudentId(homeworkAnswerDto.getStudentId());
        List<HomeworkAnswer> homeworkAnswerListBySearch = selectHomeworkAnswerListByConditionInAnd(homeworkAnswer);
        if (homeworkAnswerListBySearch.size() > 0) {
            //不是新回答
            isNewAnswer = false;
            //获取之前回答
            homeworkAnswer = homeworkAnswerListBySearch.get(0);

            if (homeworkAnswer.getHomeworkRate() != -1) {
                throw new BusinessException("该回答已被教师评分,无法修改!");
            }


            //删除数据库中之前答案图片和答案文件
            imageService.deleteImageByReferenceId(homeworkAnswer.getHomeworkAnswerId());
            fileService.deleteFileByReferenceId(homeworkAnswer.getHomeworkAnswerId());
        }

        //是新回答,则插入答案信息,否则更新答案信息
        if (isNewAnswer) {
            //新提交作业重置评风为 -1
            homeworkAnswerDto.setHomeworkRate(-1);
            //插入作业答案信息
            homeworkAnswerMapper.insert(homeworkAnswerDto);
        } else {
            //将要插入的作业答案信息添加之前回答的作业答案id
            homeworkAnswerDto.setHomeworkAnswerId(homeworkAnswer.getHomeworkAnswerId());
            //更新作业答案信息
            homeworkAnswerDto.setHomeworkRate(-1);
            homeworkAnswerMapper.updateById(homeworkAnswerDto);
        }


        //遍历作业答案中包含的图片列表,上传每一个图片
        List<String> imageUploadNameList = homeworkAnswerDto.getImageUploadNameList();
        if (imageUploadNameList != null && imageUploadNameList.size() > 0) {
            imageUploadNameList.forEach(imageName -> {
                Image imageToInsert = new Image();
                imageToInsert.setReferenceId(homeworkAnswerDto.getHomeworkAnswerId());
                imageToInsert.setImageName(imageName);
                imageMapper.insert(imageToInsert);
            });
        }

        //获取作业答案中包含的文件UUID名列表和用户自定义名
        List<String> fileUploadNameList = homeworkAnswerDto.getFileUploadNameList();
        List<String> fileUploadUserNameList = homeworkAnswerDto.getFileUploadUserNameList();
        //创建一个用于存储文件UUID名和用户自定义名的文件数组
        File[] fileArray = new File[fileUploadNameList.size()];
        //依次取出UUID名和用户自定义名,添加的新创建的文件对象中,最后将文件对象插入到文件数组中
        if (fileUploadNameList.size() == fileUploadUserNameList.size()) {
            for (int i = 0; i < fileArray.length; i++) {
                File file = new File();
                file.setReferenceId(homeworkAnswerDto.getHomeworkAnswerId());
                file.setFileName(fileUploadNameList.get(i));
                file.setFileUserName(fileUploadUserNameList.get(i));
                fileArray[i] = file;
            }
        } else {
            throw new ProgramException();
        }

        //将文件数组转换为list,依次取出每一个文件对象,插入数据库
        List<File> fileList = Arrays.asList(fileArray);
        fileList.forEach(file -> fileMapper.insert(file));

    }

    /**
     * 根据作业答案的作业答案id或作业id删除作业答案
     *
     * @param homeworkAnswer 含有要删除的作业答案信息的作业答案对象
     * @param isCoercive     是否强制删除
     */
    @Override
    public void deleteHomeworkAnswer(HomeworkAnswer homeworkAnswer, boolean isCoercive) {
        log.info("HomeworkAnswerService - deleteHomeworkAnswer :homeworkAnswer = {},isCoercive = {}", homeworkAnswer, isCoercive);

        //构建 "根据作业答案的作业答案id或作业id" 条件
        LambdaQueryWrapper<HomeworkAnswer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(homeworkAnswer.getHomeworkAnswerId() != null, HomeworkAnswer::getHomeworkAnswerId, homeworkAnswer.getHomeworkAnswerId())
                .or()
                .eq(homeworkAnswer.getHomeworkId() != null, HomeworkAnswer::getHomeworkId, homeworkAnswer.getHomeworkId());

        //搜索要删除的作业答案列表
        List<HomeworkAnswer> homeworkAnswerListBySearch = homeworkAnswerMapper.selectList(wrapper);
        if (homeworkAnswerListBySearch.size() > 0) {
            //获取每一个要删除的作业答案的作业答案id,根据作业答案id删除图片和文件
            homeworkAnswerListBySearch.forEach(homeworkAnswerItem -> {
                imageService.deleteImageByReferenceId(homeworkAnswerItem.getHomeworkAnswerId());
                fileService.deleteFileByReferenceId(homeworkAnswerItem.getHomeworkAnswerId());
            });
        }
        int affectRows = homeworkAnswerMapper.delete(wrapper);

        if (isCoercive) {
            if (affectRows > 0) {
                log.info("删除作业答案成功,影响了" + affectRows + "条数据");
            } else {
                log.error("删除答案失败,影响了" + affectRows + "条数据");
                throw new ProgramException("服务器错误,删除失败!");
            }
        }

    }

    /**
     * 更新作业评分
     *
     * @param homeworkAnswer 含有作业评分的作业答案对象
     */
    @Override
    public void updateRate(HomeworkAnswer homeworkAnswer) {
        log.info("HomeworkAnswerService - updateRate :homeworkAnswer = {}", homeworkAnswer);

        homeworkAnswerMapper.updateById(homeworkAnswer);
    }


}
