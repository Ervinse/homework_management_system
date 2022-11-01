package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ervinse.common.R;
import pers.ervinse.domain.HomeworkAnswer;
import pers.ervinse.service.HomeworkAnswerService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/homeworkAnswer")
public class HomeworkAnswerController {

    @Autowired
    private HomeworkAnswerService homeworkAnswerService;


    @GetMapping("/list")
    public R<List<HomeworkAnswer>> getHomeworkAnswerList(Long homeworkId) {
        log.info("HomeworkAnswerController - getHomeworkAnswerPage :homeworkId = {}", homeworkId);

        List<HomeworkAnswer> homeworkAnswerList = homeworkAnswerService.selectHomeworkAnswerList(homeworkId);
        return R.getSuccessInstance(homeworkAnswerList);
    }

}
