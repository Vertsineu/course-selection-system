package cn.ustc.courseselectionsystem.model.vo;

import lombok.Data;

/**
 * 学生注册响应类
 */
@Data
public class StudentLoginResponseVO {
    /**
     * token，包含username，方便查询
     */
    private String token;
}