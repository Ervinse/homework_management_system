package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import pers.ervinse.Dto.StudentDto;
import pers.ervinse.common.R;
import pers.ervinse.domain.Clase;
import pers.ervinse.domain.Student;
import pers.ervinse.service.ClaseService;
import pers.ervinse.service.StudentService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    ClaseService claseService;

    /**
     * 根据条件获取学生分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 学生分页
     */
    @GetMapping("/page")
    public R<Page<StudentDto>> getStudentPage(Integer currentPage, Integer pageSize, String searchValue) {
        log.info("StudentController - getStudentPage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        String searchValueFormatter = null;
        //输入值处理标志位
        boolean searchFlag = false;

        //当输入不为空时,处理输入值
        if (searchValue != null && !"".equals(searchValue)) {
            //将输入的班级名转化为班级id
            Clase claseToSearch = new Clase();
            claseToSearch.setClaseName(searchValue);
            List<Clase> claseList = claseService.selectClaseListByConditionInOr(claseToSearch);
            if (claseList.size() > 0){
                searchFlag = true;
                Clase clase = claseList.get(0);
                searchValueFormatter = String.valueOf(clase.getClaseId());
            }
        }
        //当输入值经过处理后,需要替换处理过的搜索值
        if (searchFlag) {
            searchValue = searchValueFormatter;
        }



        //获取学生分页数据
        Page<Student> studentPage = studentService.selectStudentPage(currentPage, pageSize, searchValue);


        //创建学生传输分页,获取班级分页中的分页数据
        Page<StudentDto> studentDtoPage = new Page<>();
        BeanUtils.copyProperties(studentPage, studentDtoPage, "records");

        //获取班级列表
        List<Student> studentPageRecords = studentPage.getRecords();
        //遍历学生列表中的每一个学生对象,获取其对应的课程传输对象,收集为list
        List<StudentDto> studentDtoPageRecords = studentPageRecords.stream().map(student -> {
            //对每一个学生,创建学生传输对象,并将每个学生数据复制到对应的传输对象中
            StudentDto studentDto = new StudentDto();
            BeanUtils.copyProperties(student, studentDto);

            //对每一个学生,根据班级id获取班级对象,并将班级名字添加到传输对象中
            Clase clase = claseService.selectClaseById(student.getClaseId());
            if (clase == null) {
                studentDto.setClaseName("未定");
            } else {
                studentDto.setClaseName(clase.getClaseName());
            }

            //抹去密码
            studentDto.setAccountPassword("");
            //返回传输对象
            return studentDto;
        }).collect(Collectors.toList());

        //为课程传输分页添加课程传输对象集合
        studentDtoPage.setRecords(studentDtoPageRecords);

        return R.getSuccessInstance(studentDtoPage);
    }


    /**
     * 根据学生id获取学生数据详情
     *
     * @param studentId 学生id
     * @return 学生数据详情
     */
    @GetMapping
    public R<Student> getStudentById(Long studentId) {
        log.info("StudentController - getStudentById :studentId = {}", studentId);

        Student student = studentService.selectStudentById(studentId);
        if (student.getClaseId() == null) {
            student.setClaseId(0L);
        }

        //抹去密码
        student.setAccountPassword("");

        return R.getSuccessInstance(student);
    }

    /**
     * 获取学生列表
     *
     * @return 学生列表
     */
    @GetMapping("/list")
    public R<List<Student>> getStudentList() {
        log.info("StudentController - getStudentList");

        List<Student> studentList = studentService.selectStudentList();

        return R.getSuccessInstance(studentList);
    }

    /**
     * 添加学生
     *
     * @param student 含有学生信息的对象
     * @return 添加结果
     */
    @Deprecated
    @PostMapping
    public R<String> addStudent(@RequestBody Student student) {
        log.info("StudentController - updateStudent :Student = {}", student);

        //对密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(student.getAccountPassword().getBytes(StandardCharsets.UTF_8));
        student.setAccountPassword(password);

        studentService.addStudent(student);
        return R.getSuccessInstance(null);
    }

    /**
     * 修改学生
     *
     * @param student 含有学生修改信息的对象
     * @return 修改结果
     */
    @PutMapping
    public R<String> updateStudent(@RequestBody Student student) {
        log.info("StudentController - updateStudent :Student = {}", student);

        //编辑学生资料时允许不输入密码
        if (student.getAccountPassword() != null){
            //对密码进行md5加密
            String password = DigestUtils.md5DigestAsHex(student.getAccountPassword().getBytes(StandardCharsets.UTF_8));
            student.setAccountPassword(password);
        }

        studentService.updateStudentById(student);
        return R.getSuccessInstance(null);
    }

    /**
     * 根据id删除学生
     *
     * @param studentId 要删除的学生id
     * @return 删除结果
     */
    @DeleteMapping
    public R<String> deleteStudent(Long studentId) {
        log.info("StudentController - deleteStudent :studentId = {}", studentId);

        studentService.deleteStudentById(studentId);
        return R.getSuccessInstance(null);
    }

}
