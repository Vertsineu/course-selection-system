package cn.ustc.courseselectionsystem.model.vo;

import lombok.Data;

@Data
public class DepartmentVO {
    private Integer id;

    private String code;
    private String name;
    private Boolean college;
}
