package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.domain.Clase;

public interface ClaseService {

    Page<Clase> selectClasePage(int currentPage, int pageSize, String searchValue);

}
