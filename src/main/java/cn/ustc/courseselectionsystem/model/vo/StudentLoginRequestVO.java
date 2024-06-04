package cn.ustc.courseselectionsystem.model.vo;

import lombok.Data;

/**
 * 学生注册请求类
 */
@Data
public class StudentLoginRequestVO {
    /**
     * 学生学号
     */
    private String username;
    /**
     * 学生密码
     */
    private String password;
}
