package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

/**
 * 学生信息类
 */
@Data
public class StudentInfoPO {
    /**
     * 学生id
     */
    private Integer id;

    /**
     * 学生学号
     */
    private String number;
    /**
     * 学生名称
     */
    private String name;
    /**
     * 学生所属院系
     */
    private DepartmentPO department;
}
