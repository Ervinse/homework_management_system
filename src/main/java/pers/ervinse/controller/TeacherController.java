package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.ervinse.common.R;
import pers.ervinse.domain.Teacher;
import pers.ervinse.service.TeacherService;

@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    /**
     * 根据条件获取教师分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 教师分页
     */
    @GetMapping("/page")
    public R<Page<Teacher>> getTeacherPage(Integer currentPage, Integer pageSize, String searchValue) {
        log.info("TeacherController - getTeacherPage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Teacher> studentPage = teacherService.selectTeacherPage(currentPage, pageSize, searchValue);

        return R.getSuccessInstance(studentPage);
    }


    /**
     * 根据教师id获取教师数据详情
     *
     * @param teacherId 教师id
     * @return 教师数据详情
     */
    @GetMapping
    public R<Teacher> getTeacherById(Long teacherId) {
        log.info("TeacherController - getTeacherById :teacherId = {}", teacherId);

        Teacher teacher = teacherService.selectTeacherById(teacherId);

        return R.getSuccessInstance(teacher);
    }

    /**
     * 添加教师
     *
     * @param teacher 含有教师信息的对象
     * @return 添加结果
     */
    @PostMapping
    public R<String> addTeacher(@RequestBody Teacher teacher) {
        log.info("TeacherController - addTeacher :Student = {}", teacher);

        teacherService.addTeacher(teacher);
        return R.getSuccessInstance(null);
    }

    /**
     * 修改教师
     *
     * @param teacher 含有学生修改信息的对象
     * @return 修改结果
     */
    @PutMapping
    public R<String> updateTeacher(@RequestBody Teacher teacher) {
        log.info("TeacherController - updateTeacher :Student = {}", teacher);

        teacherService.updateStudentById(teacher);
        return R.getSuccessInstance(null);
    }

}
