package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

@Data
public class StudentInfoPO {
    private Integer id;

    private String number;
    private String name;
    private DepartmentPO department;
}
