package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ervinse.common.R;
import pers.ervinse.domain.Homework;
import pers.ervinse.service.HomeworkService;

@Slf4j
@RestController
@RequestMapping("/homework")
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    /**
     * 根据条件获取作业分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 作业传输分页
     */
    @GetMapping("/page")
    public R<Page<Homework>> getHomeworkPage(Integer currentPage, Integer pageSize, String searchValue) {
        log.info("HomeworkController - getHomeworkPage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Homework> homeworkPage = homeworkService.selectHomeworkPage(currentPage, pageSize, searchValue);
        return R.getSuccessInstance(homeworkPage);
    }

}
