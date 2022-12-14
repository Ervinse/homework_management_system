package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.domain.Teacher;

import java.util.List;

public interface TeacherService {

    Page<Teacher> selectTeacherPage(int currentPage, int pageSize, String searchValue);

    List<Teacher> selectTeacherList();

    Teacher selectTeacherById(Long teacherId);

    Teacher selectTeacherByAccountName(Teacher teacher);

    List<Teacher> selectTeacherByConditionInOr(Teacher teacher);

    void addTeacher(Teacher teacher);

    void updateTeacherById(Teacher teacher);

    void deleteTeacherById(Long teacherId);

    void enableAccountById(Long teacherId);

    void disableAccountById(Long teacherId);

    void resetPassword(String accountName);
}
