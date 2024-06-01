package cn.ustc.courseselectionsystem.model.param;

import lombok.Data;

@Data
public class QueryClassParam {
    private String semester;
    private String departmentCode;
    private String courseCode;
    private String courseName;
    private String teacherName;
}
