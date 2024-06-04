package cn.ustc.courseselectionsystem.model.tuple;

import cn.ustc.courseselectionsystem.model.po.DepartmentPO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 部门，教师，时间地点人物组类
 */
@Data
@AllArgsConstructor
public class DepartmentTeachersTpcTuple {
    /**
     * 所有教师
     */
    private String teachers;
    /**
     * 时间地点人物
     */
    private String tpc;
    /**
     * 部门
     */
    private DepartmentPO department;
}
