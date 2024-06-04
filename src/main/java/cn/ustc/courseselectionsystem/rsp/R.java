package cn.ustc.courseselectionsystem.rsp;

import lombok.Data;

/**
 * 通用返回类
 * @param <T> 返回数据类型
 */
@Data
public class R<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态码描述
     */
    private String desc;
    /**
     * 状态信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;
}
