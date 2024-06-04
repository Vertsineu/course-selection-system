package cn.ustc.courseselectionsystem.model.vo;

import lombok.Data;

/**
 * 学生信息类
 */
@Data
public class StudentInfoVO {
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
     * 学生所属院系
     */
    private DepartmentVO department;
}
