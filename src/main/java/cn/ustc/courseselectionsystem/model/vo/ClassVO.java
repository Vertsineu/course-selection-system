package cn.ustc.courseselectionsystem.model.vo;

import lombok.Data;

@Data
public class ClassVO {
    private String code;
    private String campus;
    private String type;
    private String examMode;
    private Boolean graduateAndPostGraduate;
    private Integer periodPerWeek;
    private Integer limitCount;
    private String teachLang;
    private DepartmentVO department;
}