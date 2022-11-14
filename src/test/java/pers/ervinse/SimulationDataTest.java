package pers.ervinse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.ervinse.mapper.SimulationMapper;

@SpringBootTest
public class SimulationDataTest {

    @Autowired
    private SimulationMapper simulationMapper;

    /**
     * 测试生成全部模拟数据(包含作业,作业答案以及相关文件,图片数据)
     */
    @Test
    void generateFullSimulatedData(){
        simulationMapper.generateFullSimulatedData();
    }

    /**
     * 测试生成部分全部模拟数据(仅包含教师,学生,班级,课程数据,不包含作业,作业答案以及相关文件,图片数据)
     */
    @Test
    void generateBasicSimulatedData(){
        simulationMapper.generateBasicSimulatedData();
    }
}
