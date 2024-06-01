package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class ClassPO {
    private Integer id;
    private Integer courseId;
    private Integer departmentId;

    private String code;
    private String campus;
    private String type;
    private String examMode;
    private Boolean graduateAndPostGraduate;
    private Integer periodPerWeek;
    private Integer limitCount;
    private String teachLang;
}