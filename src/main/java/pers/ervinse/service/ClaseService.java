package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.domain.Clase;

import java.util.List;

public interface ClaseService {

    Page<Clase> selectClasePage(int currentPage, int pageSize, String searchValue);

    List<Clase> selectClaseList();
}
