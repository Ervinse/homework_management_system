package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ervinse.Dto.HomeworkDto;
import pers.ervinse.common.R;
import pers.ervinse.domain.*;
import pers.ervinse.service.ClaseCourseService;
import pers.ervinse.service.ClaseService;
import pers.ervinse.service.CourseService;
import pers.ervinse.service.HomeworkService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/homework")
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private ClaseService claseService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClaseCourseService claseCourseService;

    /**
     * 根据条件获取作业分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 作业传输分页
     */
    @GetMapping("/page")
    public R<Page<HomeworkDto>> getHomeworkPage(Integer currentPage, Integer pageSize, String searchValue) {
        log.info("HomeworkController - getHomeworkPage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);


        Page<Homework> homeworkPage = homeworkService.selectHomeworkPage(currentPage, pageSize, searchValue);

        //创建作业传输分页,获取作业分页中的分页数据
        Page<HomeworkDto> homeworkDtoPage = new Page<>();
        BeanUtils.copyProperties(homeworkPage, homeworkDtoPage, "records");

        //获取作业列表
        List<Homework> homeworkPageRecords = homeworkPage.getRecords();
        //遍历作业列表中的每一个作业对象,获取其对应的作业传输对象,收集为list
        List<HomeworkDto> homeworkDtoPageRecords = homeworkPageRecords.stream().map(homework -> {
            //对每一个作业,创建作业传输对象,并将每个作业数据复制到对应的传输对象中
            HomeworkDto homeworkDto = new HomeworkDto();
            BeanUtils.copyProperties(homework, homeworkDto);

            //对每一个作业,根据班级课程id获取班级课程对象
            ClaseCourse claseCourse = claseCourseService.selectClaseCourseById(homework.getClaseCourseId());

            //通过班级课程对象中的班级课程id,获取班级名和课程名,填入作业传输对象
            Clase clase = claseService.selectClaseById(claseCourse.getClaseId());
            Course course = courseService.selectCourseById(claseCourse.getCourseId());
            homeworkDto.setClaseName(clase.getClaseName());
            homeworkDto.setCourseName(course.getCourseName());

            //返回传输对象
            return homeworkDto;
        }).collect(Collectors.toList());

        //为课程传输分页添加课程传输对象集合
        homeworkDtoPage.setRecords(homeworkDtoPageRecords);

        return R.getSuccessInstance(homeworkDtoPage);
    }

}
