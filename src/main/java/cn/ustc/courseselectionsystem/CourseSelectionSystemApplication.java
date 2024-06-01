package cn.ustc.courseselectionsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan(basePackages = "cn.ustc.courseselectionsystem.mapper")
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@EnableTransactionManagement
@EnableConfigurationProperties
public class CourseSelectionSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseSelectionSystemApplication.class, args);
    }

}
