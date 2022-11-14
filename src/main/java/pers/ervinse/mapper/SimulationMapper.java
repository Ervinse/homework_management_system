package pers.ervinse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SimulationMapper extends BaseMapper {

    /**
     * 生成全部模拟数据
     */
    void generateFullSimulatedData();

    /**
     * 生成部分模拟数据
     */
    void generateBasicSimulatedData();
}
