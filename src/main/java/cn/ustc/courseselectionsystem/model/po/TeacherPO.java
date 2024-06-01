package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

@Data
public class TeacherPO {
    private Integer id;
    private Integer departmentId;

    private String name;
}
