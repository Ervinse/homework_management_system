package pers.ervinse;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.ervinse.domain.Clase;
import pers.ervinse.mapper.ClaseMapper;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@SpringBootTest
public class ClaseTest {

    @Autowired
    private ClaseMapper claseMapper;

    @Test
    public void selectClase(){
        List<Clase> clases = claseMapper.selectList(null);
        System.out.println(clases);
    }

    @Test
    public void insert(){
        Clase clase = new Clase();
        clase.setClaseName("123");
        clase.setTimeOfEnrollment(LocalDate.now().getYear());
        claseMapper.insert(clase);
    }
}
