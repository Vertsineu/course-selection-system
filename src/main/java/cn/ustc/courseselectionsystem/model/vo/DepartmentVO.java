package cn.ustc.courseselectionsystem.model.vo;

import lombok.Data;

/**
 * 部门类
 */
@Data
public class DepartmentVO {
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
     * 部门类别
     */
    private Boolean college;
}
