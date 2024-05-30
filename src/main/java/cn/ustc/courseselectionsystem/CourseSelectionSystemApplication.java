package cn.ustc.courseselectionsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@MapperScan(basePackages = "cn.ustc.courseselectionsystem.mapper")
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class CourseSelectionSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseSelectionSystemApplication.class, args);
    }

}
