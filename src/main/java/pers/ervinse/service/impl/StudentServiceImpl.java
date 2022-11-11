package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pers.ervinse.exception.BusinessException;
import pers.ervinse.domain.Student;
import pers.ervinse.exception.ProgramException;
import pers.ervinse.mapper.StudentMapper;
import pers.ervinse.service.StudentService;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    //账户重置新密码
    @Value("${service.resetNewPassword}")
    private String resetNewPassword;

    @Autowired
    private StudentMapper studentMapper;


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
                .like(searchValueGenderFlag, Student::getStudentGender, searchValueGender)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Student::getClaseId, searchValue);

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
    public Student selectStudentById(Long studentId) {
        log.info("StudentService - selectStudentById :studentId = {}", studentId);

        return studentMapper.selectById(studentId);
    }

    /**
     * 获取学生列表
     *
     * @return 学生列表
     */
    @Override
    public List<Student> selectStudentList() {
        log.info("StudentService - selectStudentList ");

        return studentMapper.selectList(null);
    }

    /**
     * 根据账号名获取学生
     *
     * @param student 含有账号名的学生对象
     * @return 查询到的学生对象
     */
    @Override
    public Student selectStudentByAccountName(Student student) {
        log.info("StudentService - selectStudentByAccountName :student = {}", student);

        //创建条件构造器
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.eq(StringUtils.isNotEmpty(student.getAccountName()), Student::getAccountName, student.getAccountName());

        return studentMapper.selectOne(wrapper);
    }

    /**
     * 根据条件查询学生列表
     *
     * @param student 查询的学生条件
     * @return 查询到的学生列表
     */
    @Override
    public List<Student> selectStudentListByConditionInOr(Student student) {
        log.info("StudentService - selectStudentByConditionInAnd :student = {}", student);

        //创建条件构造器
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.eq(student.getStudentId() != null, Student::getStudentId, student.getStudentId())
                .or()
                .eq(StringUtils.isNotEmpty(student.getStudentNumber()), Student::getStudentNumber, student.getStudentNumber())
                .or()
                .like(StringUtils.isNotEmpty(student.getStudentName()), Student::getStudentName, student.getStudentName())
                .or()
                .like(StringUtils.isNotEmpty(student.getAccountName()), Student::getAccountName, student.getAccountName())
                .or()
                .eq(StringUtils.isNotEmpty(student.getAccountPassword()), Student::getAccountPassword, student.getAccountPassword())
                .or()
                .eq(StringUtils.isNotEmpty(student.getStudentGender()), Student::getStudentGender, student.getStudentGender())
                .or()
                .eq(StringUtils.isNotEmpty(student.getStudentNumber()), Student::getStudentNumber, student.getStudentNumber());

        return studentMapper.selectList(wrapper);
    }

    /**
     * 添加学生
     * 当学生信息中的学号和账户名重名时,抛出sql异常
     *
     * @param student 含有学生信息的对象
     */
    @Override
    public void addStudent(Student student) {
        log.info("StudentService - addStudent :student = {}", student);

        int affectRows = studentMapper.insert(student);
        if (affectRows > 0) {
            log.info("添加学生成功,影响了" + affectRows + "条数据");
        } else {
            log.error("添加学生失败,影响了" + affectRows + "条数据");
            throw new ProgramException("服务器错误,添加失败!");
        }
    }

    /**
     * 根据id修改学生信息
     * 当学生信息中的学号和账户名重名时,抛出sql异常
     *
     * @param student 含有学生修改信息的对象
     */
    @Override
    public void updateStudentById(Student student) {
        log.info("StudentService - updateStudentById :student = {}", student);

        int affectRows = studentMapper.updateById(student);
        if (affectRows > 0) {
            log.info("修改学生成功,影响了" + affectRows + "条数据");
        } else {
            log.error("修改学生失败,影响了" + affectRows + "条数据");
            throw new ProgramException("服务器错误,修改失败!");
        }

    }

    /**
     * 根据id删除学生
     *
     * @param studentId 要删除的学生id
     */
    @Override
    public void deleteStudentById(Long studentId) {
        log.info("StudentService - deleteStudentById :studentId = {}", studentId);

        int delete = studentMapper.deleteById(studentId);
        if (delete > 0) {
            log.info("删除学生成功,影响了" + delete + "条数据");
        } else {
            log.error("删除学生失败,影响了" + delete + "条数据");
            throw new ProgramException("服务器错误,删除失败!");
        }
    }

    /**
     * 根据账户名和配置文件重置账号密码为初始密码
     *
     * @param accountName 要重置账户密码的账户名
     */
    @Override
    public void resetPassword(String accountName) {
        log.info("StudentService - resetPassword :accountName = {}", accountName);

        //将配置文件中的明文密码进行加密
        String newPassword = DigestUtils.md5DigestAsHex(resetNewPassword.getBytes(StandardCharsets.UTF_8));

        Student student = new Student();
        student.setAccountName(accountName);
        student.setAccountPassword(newPassword);

        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getAccountName, accountName);
        studentMapper.update(student, wrapper);
    }

}
