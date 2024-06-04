package cn.ustc.courseselectionsystem.model.vo;

import lombok.Data;

/**
 * 课堂类
 */
@Data
public class ClassVO {
    /**
     * 课堂id
     */
    private Integer id;

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
    /**
     * 开课院系
     */
    private DepartmentVO department;
    /**
     * 时间地点人物
     */
    private String tpc;
    /**
     * 所有教师
     */
    private String teachers;
    /**
     * 选中该课的人数
     */
    private Integer selectedCount;
    /**
     * 是否选中该课
     */
    private Boolean selected;
}