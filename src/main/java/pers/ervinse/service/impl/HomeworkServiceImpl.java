package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ervinse.Dto.HomeworkDto;
import pers.ervinse.domain.Homework;
import pers.ervinse.domain.HomeworkAnswer;
import pers.ervinse.domain.Image;
import pers.ervinse.exception.ProgramException;
import pers.ervinse.mapper.HomeworkMapper;
import pers.ervinse.mapper.ImageMapper;
import pers.ervinse.service.HomeworkAnswerService;
import pers.ervinse.service.HomeworkService;

import java.util.List;

@Slf4j
@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private HomeworkMapper homeworkMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private HomeworkAnswerService homeworkAnswerService;

    /**
     * 获取作业分页列表
     *
     * @param currentPage 当前页数
     * @param pageSize    每页条目数
     * @param searchValue 搜素值
     * @return 作业传输分页
     */
    @Override
    public Page<Homework> selectHomeworkPage(int currentPage, int pageSize, String searchValue) {
        log.info("HomeworkService - selectHomeworkPage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Homework> page = new Page<>(currentPage, pageSize);

        //创建条件构造器
        LambdaQueryWrapper<Homework> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.like(StringUtils.isNotEmpty(searchValue), Homework::getHomeworkName, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Homework::getHomeworkDescription, searchValue)
                .or()
                .eq(StringUtils.isNotEmpty(searchValue), Homework::getClaseCourseId, searchValue);
        //添加排序条件
        wrapper.orderByAsc(Homework::getHomeworkId);

        homeworkMapper.selectPage(page, wrapper);

        log.info("page信息:current = {},pages = {},size = {},total = {},records = {}", page.getCurrent(), page.getPages(), page.getSize(), page.getTotal(), page.getRecords());

        return page;
    }

    @Override
    public Homework selectHomeworkById(Long homeworkId) {
        log.info("HomeworkService - selectHomeworkListByConditionInAnd :homeworkId = {}", homeworkId);

        return homeworkMapper.selectById(homeworkId);
    }

    /**
     * 根据并条件查询作业列表
     *
     * @param homework 查询的作业条件
     * @return 查询到的作业列表
     */
    @Override
    public List<Homework> selectHomeworkListByConditionInAnd(Homework homework) {
        log.info("HomeworkService - selectHomeworkListByConditionInAnd :homework = {}", homework);

        //创建条件构造器
        LambdaQueryWrapper<Homework> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.eq(homework.getHomeworkId() != null, Homework::getHomeworkId, homework.getHomeworkId())
                .eq(StringUtils.isNotEmpty(homework.getHomeworkName()), Homework::getHomeworkName, homework.getHomeworkName())
                .eq(StringUtils.isNotEmpty(homework.getHomeworkDescription()), Homework::getHomeworkDescription, homework.getHomeworkDescription())
                .eq(homework.getClaseCourseId() != null, Homework::getClaseCourseId, homework.getClaseCourseId());


        return homeworkMapper.selectList(wrapper);
    }


    /**
     * 根据或条件查询作业列表
     *
     * @param homework 查询的作业条件
     * @return 查询到的作业列表
     */
    @Override
    public List<Homework> selectHomeworkListByConditionInOr(Homework homework) {
        log.info("HomeworkService - selectHomeworkListByConditionInOr :homework = {}", homework);

        //创建条件构造器
        LambdaQueryWrapper<Homework> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.eq(homework.getHomeworkId() != null, Homework::getHomeworkId, homework.getHomeworkId())
                .or()
                .eq(StringUtils.isNotEmpty(homework.getHomeworkName()), Homework::getHomeworkName, homework.getHomeworkName())
                .or()
                .eq(StringUtils.isNotEmpty(homework.getHomeworkDescription()), Homework::getHomeworkDescription, homework.getHomeworkDescription())
                .or()
                .eq(homework.getClaseCourseId() != null, Homework::getClaseCourseId, homework.getClaseCourseId());

        return homeworkMapper.selectList(wrapper);
    }


    /**
     * 添加作业
     *
     * @param homeworkDto 含有作业信息和图片信息的作业传输类
     */
    @Override
    @Transactional
    public void addHomework(HomeworkDto homeworkDto) {
        log.info("HomeworkService - addHomework :homeworkDto = {}", homeworkDto);

        //将作业传输对象转换为作业对象,插入数据库
        Homework homeworkToInsert = new Homework();
        homeworkToInsert.setHomeworkName(homeworkDto.getHomeworkName());
        homeworkToInsert.setHomeworkDescription(homeworkDto.getHomeworkDescription());
        homeworkToInsert.setClaseCourseId(homeworkDto.getClaseCourseId());
        int affectRows = homeworkMapper.insert(homeworkToInsert);
        //获取刚插入作业的作业id
        if (affectRows > 0) {
            List<Homework> homeworkList = selectHomeworkListByConditionInAnd(homeworkToInsert);
            if (homeworkList.size() > 0) {
                Homework homework = homeworkList.get(0);
                homeworkDto.setHomeworkId(homework.getHomeworkId());
            } else {
                throw new ProgramException("服务器错误,添加失败!");
            }
        } else {
            throw new ProgramException("服务器错误,添加失败!");
        }
        //将每一个图片插入图片表
        List<String> imageUploadNameList = homeworkDto.getImageUploadNameList();
        for (String imageUploadName : imageUploadNameList) {
            Image image = new Image();
            image.setReferenceId(homeworkDto.getHomeworkId());
            image.setImageName(imageUploadName);
            imageMapper.insert(image);
        }

        log.info("添加作业成功,影响了" + affectRows + "条数据");
    }

    /**
     * 根据作业id删除作业,对应的图片,对应的作业答案
     *
     * @param homeworkId 作业id
     * @return 删除的作业对应的图片
     */
    @Override
    @Transactional
    public List<Image> deleteHomeworkById(Long homeworkId) {
        log.info("HomeworkService - deleteHomeworkById :homeworkId = {}", homeworkId);

        //删除作业图片
        LambdaQueryWrapper<Image> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Image::getReferenceId, homeworkId);
        List<Image> imageList = imageMapper.selectList(wrapper);
        imageMapper.delete(wrapper);

        //删除与此作业相关的作业答案
        HomeworkAnswer homeworkAnswerToDelete = new HomeworkAnswer();
        homeworkAnswerToDelete.setHomeworkId(homeworkId);
        homeworkAnswerService.deleteHomeworkAnswer(homeworkAnswerToDelete, false);

        int affectRows = homeworkMapper.deleteById(homeworkId);

        if (affectRows > 0) {
            log.info("删除作业成功,影响了" + affectRows + "条数据");
        } else {
            log.error("删除作业失败,影响了" + affectRows + "条数据");
            throw new ProgramException("服务器错误,删除失败!");
        }

        return imageList;
    }

    /**
     * 根据作业条件删除作业,对应的图片,对应的作业答案
     *
     * @param homework   含有要删除的作业的信息对象
     * @param isCoercive 是否强制删除
     */
    @Override
    @Transactional
    public void deleteHomework(Homework homework, boolean isCoercive) {
        log.info("HomeworkService - deleteHomework :Homework = {},isCoercive = {}", homework,isCoercive);

        List<Homework> homeworkBySearch = selectHomeworkListByConditionInOr(homework);
        if (homeworkBySearch.size() > 0) {
            homeworkBySearch.forEach(homeworkToDelete -> {
                Long homeworkId = homeworkToDelete.getHomeworkId();

                //删除作业图片
                LambdaQueryWrapper<Image> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Image::getReferenceId, homeworkId);
                List<Image> imageList = imageMapper.selectList(wrapper);
                imageMapper.delete(wrapper);

                //删除与此作业相关的作业答案
                HomeworkAnswer homeworkAnswerToDelete = new HomeworkAnswer();
                homeworkAnswerToDelete.setHomeworkId(homeworkId);
                homeworkAnswerService.deleteHomeworkAnswer(homeworkAnswerToDelete, false);

                int affectRows = homeworkMapper.deleteById(homeworkId);

                if (isCoercive) {
                    if (affectRows > 0) {
                        log.info("删除作业成功,影响了" + affectRows + "条数据");
                    } else {
                        log.error("删除作业失败,影响了" + affectRows + "条数据");
                        throw new ProgramException("服务器错误,删除失败!");
                    }
                }
            });
        }
    }
}
