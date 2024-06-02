package cn.ustc.courseselectionsystem.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class CourseVO {
    private Integer id;

    private String code;
    private String name;
    private String category;
    private String classify;
    private String gradation;
    private String type;
    private Integer credits;
    private String education;
    private Integer periodTotal;
    private List<ClassVO> classes;
}
