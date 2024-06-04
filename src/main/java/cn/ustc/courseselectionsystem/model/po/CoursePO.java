package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程类
 */
@Data
@EqualsAndHashCode(of = "id")
public class CoursePO {
    /**
     * 课程id
     */
    private Integer id;

    /**
     * 课程代码
     */
    private String code;
    /**
     * 课程名称
     */
    private String name;
    /**
     * 课程类别
     */
    private String category;
    /**
     * 课程分类
     */
    private String classify;
    /**
     * 课程分级
     */
    private String gradation;
    /**
     * 课程类型
     */
    private String type;
    /**
     * 课程学分
     */
    private Integer credits;
    /**
     * 课程授课对象
     */
    private String education;
    /**
     * 课程总学时
     */
    private Integer periodTotal;
}
