package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

/**
 * 部门类
 */
@Data
public class DepartmentPO {
    /**
     * 部门id
     */
    private Integer id;

    /**
     * 部门代码
     */
    private String code;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门类型
     */
    private Boolean college;
}
