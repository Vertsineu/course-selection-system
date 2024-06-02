package cn.ustc.courseselectionsystem.model.vo;

import lombok.Data;

@Data
public class StudentInfoVO {
    private Integer id;

    private String number;
    private DepartmentVO department;
}
