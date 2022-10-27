package pers.ervinse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.ervinse.common.CustomException;
import pers.ervinse.domain.Clase;
import pers.ervinse.mapper.ClaseMapper;
import pers.ervinse.service.ClaseService;

import java.util.List;

@Slf4j
@Service
public class ClaseServiceImpl implements ClaseService {

    @Autowired
    private ClaseMapper claseMapper;

    @Override
    public Page<Clase> selectClasePage(int currentPage, int pageSize, String searchValue) {
        log.info("ClaseService - selectClasePage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Clase> page = new Page<>(currentPage, pageSize);

        //创建条件构造器
        LambdaQueryWrapper<Clase> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //成立条件:name值不为空时过滤条件成立
        //过滤条件:实体类对应字段 == 变量
        wrapper.like(StringUtils.isNotEmpty(searchValue), Clase::getClaseName, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Clase::getTimeOfEnrollment, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Clase::getClaseLeaderId, searchValue)
                .or()
                .like(StringUtils.isNotEmpty(searchValue), Clase::getClaseTeacherId, searchValue);
        //添加排序条件
        wrapper.orderByAsc(Clase::getClaseId);

        claseMapper.selectPage(page, wrapper);

        log.info("page信息:current = {},pages = {},size = {},total = {},records = {}", page.getCurrent(), page.getPages(), page.getSize(), page.getTotal(), page.getRecords());

        return page;
    }

    /**
     * 获取班级列表
     *
     * @return 班级列表
     */
    @Override
    public List<Clase> selectClaseList() {
        log.info("ClaseService - selectClaseList");

        return claseMapper.selectList(null);
    }

    /**
     * 添加班级
     *
     * @param clase 含有添加信息的班级对象
     */
    @Override
    public void addClase(Clase clase) {
        log.info("ClaseService - addClase : clase = {}", clase);

        int affectRows = claseMapper.insert(clase);
        if (affectRows > 0) {
            log.info("添加班级成功,影响了" + affectRows + "条数据");
        } else {
            log.error("添加班级失败,影响了" + affectRows + "条数据");
            throw new CustomException("服务器错误,添加失败!");
        }

    }
}
