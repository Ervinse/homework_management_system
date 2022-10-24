package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
     * 根据条件获取学生分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 学生分页
     */
    @GetMapping("/page")
    public R<Page<Teacher>> getTeacherPage(Integer currentPage, Integer pageSize, String searchValue) {
        log.info("TeacherController - getTeacherPage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Teacher> studentPage = teacherService.selectTeacherPage(currentPage, pageSize, searchValue);

        return R.getSuccessInstance(studentPage);
    }

}
