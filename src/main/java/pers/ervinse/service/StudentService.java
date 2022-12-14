package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.domain.Student;

import java.util.List;

public interface StudentService {

    Page<Student> selectStudentPage(int currentPage, int pageSize, String name);

    Student selectStudentById(Long studentId);

    List<Student> selectStudentList();

    Student selectStudentByAccountName(Student student);

    List<Student> selectStudentListByConditionInOr(Student student);

    void addStudent(Student student);

    void updateStudentById(Student student);

    void deleteStudentById(Long studentId);

    void resetPassword(String accountName);
}
