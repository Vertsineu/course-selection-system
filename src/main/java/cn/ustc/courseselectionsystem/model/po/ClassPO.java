package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课堂类
 */
@Data
@EqualsAndHashCode(of = "id")
public class ClassPO {
    /**
     * 课堂id
     */
    private Integer id;
    /**
     * 课堂所属的学院id
     */
    private Integer courseId;
    /**
     * 课堂所属的学院id
     */
    private Integer departmentId;

    /**
     * 课堂代码
     */
    private String code;
    /**
     * 上课校区
     */
    private String campus;
    /**
     * 课堂类型
     */
    private String type;
    /**
     * 考试模式
     */
    private String examMode;
    private Boolean graduateAndPostGraduate;
    /**
     * 每周学时
     */
    private Integer periodPerWeek;
    /**
     * 限选人数
     */
    private Integer limitCount;
    /**
     * 教学语言
     */
    private String teachLang;
}