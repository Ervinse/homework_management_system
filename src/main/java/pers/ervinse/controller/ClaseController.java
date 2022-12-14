package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.ervinse.Dto.ClaseDto;
import pers.ervinse.Dto.CourseDto;
import pers.ervinse.common.R;
import pers.ervinse.domain.*;
import pers.ervinse.service.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/clase")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClaseCourseService claseCourseService;

    /**
     * 根据条件获取班级分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 班级分页
     */
    @GetMapping("/page")
    public R<Page<ClaseDto>> getClasePage(Integer currentPage, Integer pageSize, String searchValue) {
        log.info("ClaseController - getClasePage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        String searchValueFormatter = null;
        //输入值处理标志位
        boolean searchFlag = false;

        //当输入不为空时,处理输入值
        if (searchValue != null && !"".equals(searchValue)) {
            //将输入的学生名转化为学生id
            Student studentToSearch = new Student();
            studentToSearch.setStudentName(searchValue);
            List<Student> studentList = studentService.selectStudentListByConditionInOr(studentToSearch);
            if (studentList.size() > 0) {
                searchFlag = true;
                Student student = studentList.get(0);
                searchValueFormatter = String.valueOf(student.getStudentId());
            }
            //将输入的教师名转化为教师id
            Teacher teacherToSearch = new Teacher();
            teacherToSearch.setTeacherName(searchValue);
            List<Teacher> teacherList = teacherService.selectTeacherByConditionInOr(teacherToSearch);
            if (teacherList.size() > 0) {
                searchFlag = true;
                Teacher teacher = teacherList.get(0);
                searchValueFormatter = String.valueOf(teacher.getTeacherId());
            }
        }

        //当输入值经过处理后,需要替换处理过的搜索值
        if (searchFlag) {
            searchValue = searchValueFormatter;
        }
        //获取班级列表分页
        Page<Clase> clasePage = claseService.selectClasePage(currentPage, pageSize, searchValue);

        //创建班级传输分页,获取班级分页中的分页数据
        Page<ClaseDto> claseDtoPage = new Page<>();
        BeanUtils.copyProperties(clasePage, claseDtoPage, "records");

        //获取班级列表
        List<Clase> clasePageRecords = clasePage.getRecords();
        //遍历课程列表中的每一个课程对象,获取其对应的课程传输对象,收集为list
        List<ClaseDto> claseDtoPageRecords = clasePageRecords.stream().map(clase -> {
            //对每一个班级,创建班级传输对象,并将每个班级数据复制到对应的传输对象中
            ClaseDto claseDto = new ClaseDto();
            BeanUtils.copyProperties(clase, claseDto);

            //对每一个班级,根据教师id获取教师对象,并将教师名字添加到传输对象中
            Teacher teacher = teacherService.selectTeacherById(clase.getClaseTeacherId());
            claseDto.setClaseTeacherName(teacher.getTeacherName());

            //对每一个班级,根据学生id获取教师对象,并将学生名字添加到传输对象中
            Student student = studentService.selectStudentById(clase.getClaseLeaderId());
            claseDto.setClaseLeaderName(student.getStudentName());

            //返回传输对象
            return claseDto;
        }).collect(Collectors.toList());

        //为课程传输分页添加课程传输对象集合
        claseDtoPage.setRecords(claseDtoPageRecords);

        return R.getSuccessInstance(claseDtoPage);
    }

    /**
     * 获取班级列表
     *
     * @return 班级列表
     */
    @GetMapping("/list")
    public R<List<Clase>> getCLaseList() {
        log.info("ClaseController - getCLaseList");

        List<Clase> claseList = claseService.selectClaseList();

        return R.getSuccessInstance(claseList);
    }

    /**
     * 获取班级详情
     * @param claseId 班级id
     * @return 班级详情数据
     */
    @GetMapping
    public R<ClaseDto> getClaseDetail(Long claseId) {
        log.info("ClaseController - getClaseDetail : claseId = {}", claseId);

        //根据id获取班级
        Clase clase = claseService.selectClaseById(claseId);
        //根据学生id和教师id获取班主任和班长名字
        Teacher teacher = teacherService.selectTeacherById(clase.getClaseTeacherId());
        Student student = studentService.selectStudentById(clase.getClaseLeaderId());
        //对传输对象设置班主任和班长名字,拷贝班级对象其他属性
        ClaseDto claseDto = new ClaseDto();
        claseDto.setClaseLeaderName(student.getStudentName());
        claseDto.setClaseTeacherName(teacher.getTeacherName());
        BeanUtils.copyProperties(clase, claseDto);

        //从claseCourse表中根据班级id查询对班级课程集合
        ClaseCourse claseCourse = new ClaseCourse();
        claseCourse.setClaseId(claseId);
        List<ClaseCourse> claseCourseList = claseCourseService.selectClaseCourseByConditionInAnd(claseCourse);
        //对每一个班级课程对象在课程表中查询课程,收集为课程传输对象集合
        List<CourseDto> courseListBySearch = claseCourseList.stream().map(claseCourseItem -> {
            //获取对应的课程对象
            Course course = courseService.selectCourseById(claseCourseItem.getCourseId());
            //获取课程对应的任课老师姓名
            Teacher teacherBySelect = teacherService.selectTeacherById(course.getCourseTeacherId());
            //将课程属性和任课老师姓名拷贝到课程传输对象中
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course,courseDto);
            courseDto.setCourseTeacherName(teacherBySelect.getTeacherName());
            return courseDto;
        }).collect(Collectors.toList());

        //将课程传输对象添加到要查询的班级对象中
        claseDto.setCourseList(courseListBySearch);

        return R.getSuccessInstance(claseDto);
    }


    /**
     * 添加班级和对应的课程
     *
     * @param claseDto 含有班级添加信息和课程信息的班级传输对象
     * @return 添加结果
     */
    @PostMapping
    public R<String> addClase(@RequestBody ClaseDto claseDto) {
        log.info("ClaseController - addClase :claseDto = {}", claseDto);

        claseService.addClase(claseDto);
        return R.getSuccessOperationInstance();
    }

    /**
     * 修改班级和对应的课程
     * @param claseDto 含有班级修改信息和课程信息的班级传输对象
     * @return 修改结果
     */
    @PutMapping
    public R<String> updateClase(@RequestBody ClaseDto claseDto) {
        log.info("ClaseController - updateClase :claseDto = {}", claseDto);

        claseService.updateClase(claseDto);
        return R.getSuccessOperationInstance();
    }



    @DeleteMapping
    public R<String> deleteClase(Long claseId){
        log.info("ClaseController - deleteClase :claseId = {}", claseId);

        claseService.deleteClaseById(claseId);
        return R.getSuccessOperationInstance();

    }
}
