package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.ervinse.Dto.HomeworkDto;
import pers.ervinse.common.CustomException;
import pers.ervinse.common.R;
import pers.ervinse.domain.*;
import pers.ervinse.service.*;

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

    @Autowired
    private ImageService imageService;

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


        String searchValueFormatter = null;
        //输入值处理标志位
        //第一次搜索标志
        boolean searchFlag1 = false;
        //第二次搜素标志
        boolean searchFlag2 = false;

        //当输入不为空时,处理输入值
        if (searchValue != null && !"".equals(searchValue)) {
            //假设搜索值为班级名,搜索班级
            Clase claseToSearch = new Clase();
            claseToSearch.setClaseName(searchValue);
            List<Clase> claseList = claseService.selectClaseListByConditionInOr(claseToSearch);
            //搜索到班级
            if (claseList.size() > 0) {
                searchFlag1 = true;
                Clase clase = claseList.get(0);
                log.info("search - clase = {}", clase);
                //以搜索到的班级id为值,继续搜索班级课程表
                ClaseCourse claseCourseToSearch = new ClaseCourse();
                claseCourseToSearch.setClaseId(clase.getClaseId());
                List<ClaseCourse> claseCourseList = claseCourseService.selectClaseCourseByConditionInAnd(claseCourseToSearch);
                //搜索到班级课程对象,保存班级课程id
                if (claseCourseList.size() > 0) {
                    searchFlag2 = true;
                    ClaseCourse claseCourse = claseCourseList.get(0);
                    log.info("search - claseCourse = {}", claseCourse);
                    searchValueFormatter = String.valueOf(claseCourse.getClaseCourseId());
                }
            }

            //假设搜索值为课程名,搜索课程
            Course courseToSearch = new Course();
            courseToSearch.setCourseName(searchValue);
            List<Course> courseList = courseService.selectCourseByConditionInOR(courseToSearch);
            //搜素到课程
            if (courseList.size() > 0) {
                searchFlag1 = true;
                Course course = courseList.get(0);
                log.info("search - course = {}", course);
                //以搜索到的课程id为值,继续搜索班级课程表
                ClaseCourse claseCourseToSearch = new ClaseCourse();
                claseCourseToSearch.setCourseId(course.getCourseId());
                List<ClaseCourse> claseCourseList = claseCourseService.selectClaseCourseByConditionInAnd(claseCourseToSearch);
                //搜索到班级课程对象,保存班级课程id
                if (claseCourseList.size() > 0) {
                    searchFlag2 = true;
                    ClaseCourse claseCourse = claseCourseList.get(0);
                    log.info("search - claseCourse = {}", claseCourse);
                    searchValueFormatter = String.valueOf(claseCourse.getClaseCourseId());
                }
            }
        }
        //当输入值经过处理后,需要替换处理过的搜索值
        if (searchFlag1 && searchFlag2) {
            searchValue = searchValueFormatter;
        }


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

    @GetMapping
    public R<HomeworkDto> getHomeworkById(Long homeworkId) {
        log.info("HomeworkController - getHomeworkById :homeworkId = {}", homeworkId);

        Homework homework = homeworkService.selectHomeworkById(homeworkId);
        Image imageToSearch = new Image();
        imageToSearch.setReferenceId(homeworkId);
        List<Image> imageList = imageService.selectImageListByConditionInOr(imageToSearch);
        List<String> imageNameList = imageList.stream().map(image -> {
            String imageName = image.getImageName();
            return imageName;
        }).collect(Collectors.toList());
        HomeworkDto homeworkDto = new HomeworkDto();
        BeanUtils.copyProperties(homework,homeworkDto);
        homeworkDto.setImageUploadNameList(imageNameList);
        return R.getSuccessInstance(homeworkDto);
    }

    /**
     * 添加作业
     *
     * @param homeworkDto 含有作业信息和图片信息的作业传输类
     * @return 添加结果
     */
    @PostMapping
    public R<String> addHomework(@RequestBody HomeworkDto homeworkDto) {
        log.info("HomeworkController - addHomework :homeworkDto = {}", homeworkDto);

        ClaseCourse claseCourse = new ClaseCourse();
        claseCourse.setCourseId(homeworkDto.getCourseId());
        claseCourse.setClaseId(homeworkDto.getClaseId());
        List<ClaseCourse> claseCourseList = claseCourseService.selectClaseCourseByConditionInAnd(claseCourse);
        if (claseCourseList.size() > 0) {
            homeworkDto.setClaseCourseId(claseCourseList.get(0).getClaseCourseId());
        } else {
            throw new CustomException("该班级没有此课程,请重试!");
        }
        homeworkService.addHomework(homeworkDto);

        return R.getSuccessOperationInstance();
    }

}
