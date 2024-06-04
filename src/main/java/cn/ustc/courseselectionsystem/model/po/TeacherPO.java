package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

/**
 * 教师类
 */
@Data
public class TeacherPO {
    /**
     * 教师id
     */
    private Integer id;
    /**
     * 教师所属院系id
     */
    private Integer departmentId;

    /**
     * 教师姓名
     */
    private String name;
}
