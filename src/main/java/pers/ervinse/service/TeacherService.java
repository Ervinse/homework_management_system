package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.domain.Teacher;

public interface TeacherService {

    Page<Teacher> selectTeacherPage(int currentPage, int pageSize, String searchValue);

    Teacher selectTeacherById(Long teacherId);

    Teacher selectTeacherByAccountName(Teacher teacher);

    void addTeacher(Teacher teacher);

    void updateStudentById(Teacher teacher);

    void deleteTeacherById(Long teacherId);

    void enableAccountById(Long teacherId);

    void disableAccountById(Long teacherId);
}
