package cn.ustc.courseselectionsystem.model.param;

import lombok.Data;

/**
 * 查询课程所需的参数
 */
@Data
public class QueryClassMapperParam {
    /**
     * 课程所属的学院代码
     */
    private String departmentCode;
    /**
     * 课程代码
     */
    private String courseCode;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 教师名称
     */
    private String teacherName;
}
