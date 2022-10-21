package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.domain.Student;

public interface StudentService {

    Page<Student> selectStudentPage(int currentPage, int pageSize, String name);

    Student selectStudentById(String studentId);

    Student selectStudentByCondition(Student student);

    int addStudent(Student student);
}
