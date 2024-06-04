package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

/**
 * 学生注册类
 */
@Data
public class StudentRegisterPO {
    /**
     * 学生id
     */
    private Integer id;

    /**
     * 学生学号
     */
    private String number;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 学生密码
     */
    private String password;
    /**
     * 学生所属院系id
     */
    private Integer departmentId;
}
