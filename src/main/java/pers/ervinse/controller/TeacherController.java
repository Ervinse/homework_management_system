package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import pers.ervinse.common.R;
import pers.ervinse.domain.Teacher;
import pers.ervinse.service.TeacherService;

import java.nio.charset.StandardCharsets;
import java.util.List;

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
     * 获取教师列表
     * @return 教师列表
     */
    @GetMapping("/list")
    public R<List<Teacher>> getTeacherList(){
        log.info("TeacherController - getTeacherList");

        List<Teacher> teacherList = teacherService.selectTeacherList();

        return R.getSuccessInstance(teacherList);
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
        log.info("TeacherController - addTeacher :teacher = {}", teacher);

        //对密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(teacher.getAccountPassword().getBytes(StandardCharsets.UTF_8));
        teacher.setAccountPassword(password);

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
        log.info("TeacherController - updateTeacher :teacher = {}", teacher);

        //编辑教师资料时允许不输入密码
        if (StringUtils.isEmpty(teacher.getAccountPassword())){
            teacher.setAccountPassword("123456");
        }

        //对密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(teacher.getAccountPassword().getBytes(StandardCharsets.UTF_8));
        teacher.setAccountPassword(password);
        teacherService.updateTeacherById(teacher);
        return R.getSuccessInstance(null);
    }

    /**
     * 根据id删除教师
     *
     * @param teacherId 要删除的教师id
     * @return 删除结果
     */
    @DeleteMapping
    public R<String> deleteTeacher(Long teacherId) {
        log.info("TeacherController - deleteTeacher :teacherId = {}", teacherId);

        teacherService.deleteTeacherById(teacherId);
        return R.getSuccessInstance(null);
    }

    /**
     * 根据教师id启用账户
     *
     * @param teacherId 教师id
     * @return 启用结果
     */
    @PutMapping("/enableAccount")
    public R<String> enableAccount(Long teacherId) {
        log.info("TeacherController - enableAccount :teacherId = {}", teacherId);

        teacherService.enableAccountById(teacherId);
        return R.getSuccessInstance(null);
    }

    /**
     * 根据教师id禁用账户
     *
     * @param teacherId 教师id
     * @return 禁用结果
     */
    @PutMapping("/disableAccount")
    public R<String> disableAccount(Long teacherId) {
        log.info("TeacherController - disableAccount :teacherId = {}", teacherId);

        teacherService.disableAccountById(teacherId);
        return R.getSuccessInstance(null);
    }

}
