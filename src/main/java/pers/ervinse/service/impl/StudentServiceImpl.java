package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.common.CustomException;
import pers.ervinse.domain.Student;
import pers.ervinse.mapper.StudentMapper;
import pers.ervinse.service.StudentService;

import java.util.Objects;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    /**
     * 根据条件获取学生分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 学生分页
     */
    @Override
    public Page<Student> selectStudentPage(int currentPage, int pageSize, String searchValue) {
        log.info("StudentService - selectStudentPage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Student> page = new Page<>(currentPage, pageSize);

        //当用户输入"男"或"女"时,则需要将输入值转换为对应的"1"或"2"进行搜索性别字段
        String searchValueGender = "";
        boolean searchValueGenderFlag = false;
        if ("男".equals(searchValue)) {
            searchValueGender = "1";
            searchValueGenderFlag = true;
        } else if ("女".equals(searchValue)) {
            searchValueGender = "2";
            searchValueGenderFlag = true;
        }

        //创建条件构造器
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.like(StringUtils.isNotEmpty(searchValue), Student::getStudentNumber, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Student::getStudentName, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Student::getAccountName, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Student::getPhoneNumber, searchValue)
                .or()
                .like(searchValueGenderFlag, Student::getStudentGender, searchValueGender);
        //添加排序条件
        wrapper.orderByAsc(Student::getStudentId);

        studentMapper.selectPage(page, wrapper);

        log.info("page信息:current = {},pages = {},size = {},total = {},records = {}", page.getCurrent(), page.getPages(), page.getSize(), page.getTotal(), page.getRecords());

        return page;
    }


    /**
     * 根据学生id获取学生数据详情
     *
     * @param studentId 学生id
     * @return 学生数据详情
     */
    @Override
    public Student selectStudentById(String studentId) {
        log.info("StudentService - selectStudentById :studentId = {}", studentId);

        return studentMapper.selectById(studentId);
    }

    @Override
    public Student selectStudentByCondition(Student student) {
        log.info("StudentService - selectStudentByCondition :student = {}", student);

        return null;
    }

    @Override
    public void addStudent(Student student) {
        log.info("StudentService - addStudent :student = {}", student);

        Student studentByStudentNumberAndAccountName = studentMapper.getStudentByStudentNumberAndAccountName(student);

        log.debug("studentByStudentNumberAndAccountName = {}", studentByStudentNumberAndAccountName);

        if (studentByStudentNumberAndAccountName == null) {
            int insert = studentMapper.insert(student);
            if (insert > 0) {
                log.info("添加学生成功,影响了" + insert + "条数据");
            } else {
                log.info("添加学生失败,影响了" + insert + "条数据");
                throw new CustomException("服务器错误,添加失败!");
            }
        }
        //学号重复
        else if (Objects.equals(studentByStudentNumberAndAccountName.getStudentNumber(), student.getStudentNumber())) {
            throw new CustomException("学号重复!");
            //账号名重复
        } else if (Objects.equals(studentByStudentNumberAndAccountName.getAccountName(), student.getAccountName())) {
            throw new CustomException("账号名重复!");
        } else {
            throw new CustomException("未知错误!");
        }
    }

}
