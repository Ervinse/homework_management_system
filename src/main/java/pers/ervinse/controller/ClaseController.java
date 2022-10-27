package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ervinse.Dto.ClaseDto;
import pers.ervinse.Dto.CourseDto;
import pers.ervinse.common.R;
import pers.ervinse.domain.Clase;
import pers.ervinse.domain.Course;
import pers.ervinse.domain.Student;
import pers.ervinse.domain.Teacher;
import pers.ervinse.service.ClaseService;
import pers.ervinse.service.StudentService;
import pers.ervinse.service.TeacherService;

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

    @GetMapping("/page")
    public R<Page<ClaseDto>> getClasePage(Integer currentPage, Integer pageSize, String searchValue) {
        log.info("ClaseController - getClasePage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        String searchValueFormatter = null;

        //当输入不为空时,处理输入值
        if (searchValue != null) {
            //将输入的学生名转化为学生id
            Student studentToSearch = new Student();
            studentToSearch.setStudentName(searchValue);
            List<Student> studentList = studentService.selectStudentByConditionInOr(studentToSearch);
            if (studentList.size() > 0) {
                Student student = studentList.get(0);
                searchValueFormatter = String.valueOf(student.getStudentId());
            }
            //将输入的教师名转化为教师id
            Teacher teacherToSearch = new Teacher();
            teacherToSearch.setTeacherName(searchValue);
            List<Teacher> teacherList = teacherService.selectTeacherByConditionInOr(teacherToSearch);
            if (teacherList.size() > 0){
                Teacher teacher = teacherList.get(0);
                searchValueFormatter = String.valueOf(teacher.getTeacherId());
            }
        }

        searchValue = searchValueFormatter;
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
}
