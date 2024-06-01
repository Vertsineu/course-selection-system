package cn.ustc.courseselectionsystem.model.param;

import lombok.Data;

@Data
public class QueryClassMapperParam {
    private String departmentCode;
    private String courseCode;
    private String courseName;
    private String teacherName;
}
