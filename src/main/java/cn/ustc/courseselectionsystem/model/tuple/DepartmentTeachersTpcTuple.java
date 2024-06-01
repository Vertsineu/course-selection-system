package cn.ustc.courseselectionsystem.model.tuple;

import cn.ustc.courseselectionsystem.model.po.DepartmentPO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentTeachersTpcTuple {
    private String teachers;
    private String tpc;
    private DepartmentPO department;
}
