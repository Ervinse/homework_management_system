package pers.ervinse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ervinse.common.R;
import pers.ervinse.domain.Clase;
import pers.ervinse.service.ClaseService;

@Slf4j
@RestController
@RequestMapping("/clase")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @GetMapping("/page")
    public R<Page<Clase>> getClasePage(Integer currentPage, Integer pageSize, String searchValue) {
        log.info("ClaseController - getClasePage :currentPage = {},pageSize = {},searchValue = {}", currentPage, pageSize, searchValue);

        Page<Clase> clasePage = claseService.selectClasePage(currentPage, pageSize, searchValue);

        return R.getSuccessInstance(clasePage);
    }
}
