package pers.ervinse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
//扫描过滤器
@ServletComponentScan
@SpringBootApplication
public class HomeworkManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkManagementSystemApplication.class, args);
    }

}
