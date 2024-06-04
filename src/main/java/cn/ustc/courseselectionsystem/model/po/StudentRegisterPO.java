package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

@Data
public class StudentRegisterPO {
    private Integer id;

    private String number;
    private String name;
    private String password;
    private Integer departmentId;
}
