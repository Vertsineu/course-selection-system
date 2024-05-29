package cn.ustc.courseselectionsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.ustc.courseselectionsystem.mapper")
public class CourseSelectionSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseSelectionSystemApplication.class, args);
    }

}
