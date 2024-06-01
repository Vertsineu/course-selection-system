package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

@Data
public class DepartmentPO {
    private Integer id;

    private String code;
    private String name;
    private Boolean college;
}
