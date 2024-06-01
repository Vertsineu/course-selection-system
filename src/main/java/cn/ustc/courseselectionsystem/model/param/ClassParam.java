package cn.ustc.courseselectionsystem.model.param;

import lombok.Data;

@Data
public class ClassParam {
    private String semester;
    private String departmentCode;
    private String courseCode;
    private String courseName;
    private String teacherName;
}
