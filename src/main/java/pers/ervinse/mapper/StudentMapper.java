package pers.ervinse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.ervinse.domain.Student;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 根据学号和账号名获取学生,判断学号和账号是否重名
     * @param student 条件
     * @return 重名账号
     */
    @Deprecated
    List<Student> getStudentByStudentNumberAndAccountName(Student student);
}
