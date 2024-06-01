package cn.ustc.courseselectionsystem.model.vo;

import java.util.Collection;

import lombok.Data;

@Data
public class CourseVO {
    private String code;
    private String name;
    private String category;
    private String classify;
    private String gradation;
    private String type;
    private Integer credit;
    private String education;
    private Integer periodTotal;
    private Collection<ClassVO> classes;
}
