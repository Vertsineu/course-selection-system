package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

@Data
public class StudentLoginPO {
    private Integer id;

    private String number;
    private String password;
    private Integer departmentId; 
}
