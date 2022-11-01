package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ervinse.Dto.HomeworkAnswerDto;
import pers.ervinse.common.R;
import pers.ervinse.domain.HomeworkAnswer;
import pers.ervinse.domain.Student;
import pers.ervinse.service.HomeworkAnswerService;
import pers.ervinse.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/homeworkAnswer")
public class HomeworkAnswerController {

    @Autowired
    private HomeworkAnswerService homeworkAnswerService;

    @Autowired
    private StudentService studentService;


    /**
     * 根据作业id获取作业答案列表
     * @param homeworkId 作业id
     * @return 作业答案列表
     */
    @GetMapping("/list")
    public R<List<HomeworkAnswerDto>> getHomeworkAnswerList(Long homeworkId) {
        log.info("HomeworkAnswerController - getHomeworkAnswerPage :homeworkId = {}", homeworkId);

        List<HomeworkAnswer> homeworkAnswerList = homeworkAnswerService.selectHomeworkAnswerList(homeworkId);
        List<HomeworkAnswerDto> homeworkAnswerDtoList = homeworkAnswerList.stream().map(homeworkAnswer -> {
            Student student = studentService.selectStudentById(homeworkAnswer.getStudentId());
            HomeworkAnswerDto homeworkAnswerDto = new HomeworkAnswerDto();
            BeanUtils.copyProperties(homeworkAnswer,homeworkAnswerDto);
            homeworkAnswerDto.setStudentName(student.getStudentName());
            return homeworkAnswerDto;
        }).collect(Collectors.toList());
        return R.getSuccessInstance(homeworkAnswerDtoList);
    }

}
