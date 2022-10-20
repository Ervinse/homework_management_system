package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.domain.Student;
import pers.ervinse.mapper.StudentMapper;
import pers.ervinse.service.StudentService;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    /**
     * 根据条件获取学生分页
     * @param currentPage 当前页
     * @param pageSize 每页条数
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

}
