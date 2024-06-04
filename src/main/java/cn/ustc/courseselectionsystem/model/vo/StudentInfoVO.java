package cn.ustc.courseselectionsystem.model.vo;

import lombok.Data;

@Data
public class StudentInfoVO {
    private Integer id;

    private String number;
    private String name;
    private DepartmentVO department;
}
