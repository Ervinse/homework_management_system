package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.domain.Student;

public interface StudentService {

    Page<Student> selectStudentPage(int currentPage, int pageSize, String name);

    Student selectStudentById(Long studentId);

    Student selectStudentByAccountName(Student student);

    Student selectStudentByCondition(Student student);

    void addStudent(Student student);

    void updateStudentById(Student student);

    void deleteStudentById(Long studentId);
}
