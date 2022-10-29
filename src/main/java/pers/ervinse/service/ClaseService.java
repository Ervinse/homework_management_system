package pers.ervinse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ervinse.Dto.ClaseDto;
import pers.ervinse.domain.Clase;

import java.util.List;

public interface ClaseService {

    Page<Clase> selectClasePage(int currentPage, int pageSize, String searchValue);

    List<Clase> selectClaseList();

    Clase selectClaseById(Long claseId);

    void addClase(ClaseDto claseDto);

    void updateClase(ClaseDto claseDto);

    void deleteClaseById(Long claseId);
}
