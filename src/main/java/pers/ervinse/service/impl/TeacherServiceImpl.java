package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.common.CustomException;
import pers.ervinse.domain.Teacher;
import pers.ervinse.mapper.TeacherMapper;
import pers.ervinse.service.TeacherService;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 根据条件获取教师分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param searchValue 搜索值
     * @return 教师分页
     */
    @Override
    public Page<Teacher> selectTeacherPage(int currentPage, int pageSize, String searchValue) {
        log.info("TeacherService - selectTeacherPage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Teacher> page = new Page<>(currentPage, pageSize);


        String searchValueGender = "";
        String searchValueAccountType = "";
        String searchValueAccountStatus = "";
        boolean searchValueGenderFlag = false;
        boolean searchValueAccountTypeFlag = false;
        boolean searchValueAccountStatusFlag = false;

        //当用户输入"男"或"女"时,则需要将输入值转换为对应的"1"或"2"进行搜索性别字段
        if ("男".equals(searchValue)) {
            searchValueGender = "1";
            searchValueGenderFlag = true;
        } else if ("女".equals(searchValue)) {
            searchValueGender = "2";
            searchValueGenderFlag = true;

        //当用户输入"管理员"或"用户"时,则需要将输入值转换为对应的"1"或"0"进行搜索账户类型字段
        } else if ("管理员".equals(searchValue)) {
            searchValueAccountType = "1";
            searchValueAccountTypeFlag = true;
        } else if ("用户".equals(searchValue)) {
            searchValueAccountType = "0";
            searchValueAccountTypeFlag = true;

        //当用户输入"启用"或"禁用"时,则需要将输入值转换为对应的"1"或"0"进行搜索账户状态字段
        }else if ("启用".equals(searchValue)) {
            searchValueAccountStatus = "1";
            searchValueAccountStatusFlag = true;
        } else if ("禁用".equals(searchValue)) {
            searchValueAccountStatus = "0";
            searchValueAccountStatusFlag = true;
        }

        //创建条件构造器
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.like(StringUtils.isNotEmpty(searchValue), Teacher::getTeacherGender, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Teacher::getTeacherName, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Teacher::getAccountName, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Teacher::getPhoneNumber, searchValue)
                .or()
                .like(searchValueGenderFlag, Teacher::getTeacherGender, searchValueGender)
                .or()
                .like(searchValueAccountTypeFlag, Teacher::getAccountType, searchValueAccountType)
                .or()
                .like(searchValueAccountStatusFlag, Teacher::getAccountStatus, searchValueAccountStatus);
        //添加排序条件
        wrapper.orderByAsc(Teacher::getTeacherId);

        teacherMapper.selectPage(page, wrapper);

        log.info("page信息:current = {},pages = {},size = {},total = {},records = {}", page.getCurrent(), page.getPages(), page.getSize(), page.getTotal(), page.getRecords());

        return page;

    }

    /**
     * 根据教师id获取学生数据详情
     * @param teacherId 教师id
     * @return 教师数据详情
     */
    @Override
    public Teacher selectTeacherById(Long teacherId) {
        log.info("TeacherService - selectTeacherById :teacherId = {}", teacherId);

        return teacherMapper.selectById(teacherId);
    }


    /**
     * 添加教师
     * 当教师信息中的学号和账户名重名时,抛出sql异常
     *
     * @param teacher 含有教师信息的对象
     */
    @Override
    public void addTeacher(Teacher teacher) {
        log.info("TeacherService - addTeacher :student = {}", teacher);

        int affectRows = teacherMapper.insert(teacher);
        if (affectRows > 0) {
            log.info("添加教师成功,影响了" + affectRows + "条数据");
        } else {
            log.error("添加教师失败,影响了" + affectRows + "条数据");
            throw new CustomException("服务器错误,添加失败!");
        }
    }
}
