package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    /**
     * 根据作业答案id获取作业答案
     * @param homeworkAnswerId 作业答案id
     * @return 作业答案对象
     */
    @GetMapping
    public R<HomeworkAnswerDto> getHomeworkAnswerById(Long homeworkAnswerId){
        log.info("HomeworkAnswerController - getHomeworkAnswerById :homeworkAnswerId = {}", homeworkAnswerId);

        HomeworkAnswer homeworkAnswer = homeworkAnswerService.selectHomeworkAnswerById(homeworkAnswerId);
        Student student = studentService.selectStudentById(homeworkAnswer.getStudentId());
        HomeworkAnswerDto homeworkAnswerDto = new HomeworkAnswerDto();
        homeworkAnswerDto.setStudentName(student.getStudentName());
        BeanUtils.copyProperties(homeworkAnswer,homeworkAnswerDto);
        return R.getSuccessInstance(homeworkAnswerDto);
    }

    /**
     * 添加作业答案
     * @param homeworkAnswerDto 含有所属作业信息和提交相关信息的作业答案传输对象
     * @return 添加作业答案响应
     */
    @PostMapping
    public R<String> addHomeworkAnswer(@RequestBody HomeworkAnswerDto homeworkAnswerDto){
        log.info("HomeworkAnswerController - addHomeworkAnswer :homeworkAnswerDto = {}", homeworkAnswerDto);

        homeworkAnswerService.addHomeworkAnswer(homeworkAnswerDto);

        return R.getSuccessOperationInstance();

    }

    /**
     * 更新作业评分
     * @param homeworkAnswer 含有作业评分的作业答案对象
     * @return 更新作业评分响应
     */
    @PutMapping("/updateRate")
    public R<String> updateRate(@RequestBody HomeworkAnswer homeworkAnswer){
        log.info("HomeworkAnswerController - updateRate :homeworkAnswer = {}", homeworkAnswer);

        homeworkAnswerService.updateRate(homeworkAnswer);
        return R.getSuccessOperationInstance();
    }
}
