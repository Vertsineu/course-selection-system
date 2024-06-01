package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class CoursePO {
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
}
