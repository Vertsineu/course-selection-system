package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

/**
 * 学生登录类
 */
@Data
public class StudentLoginPO {
    /**
     * 学生id
     */
    private Integer id;

    /**
     * 学生学号
     */
    private String number;
    /**
     * 学生密码
     */
    private String password;
}
