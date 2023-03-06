package edu.nuist;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("edu.nuist.dao")
public class ExperimentWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExperimentWebApplication.class, args);
    }

}
