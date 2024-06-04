package cn.ustc.courseselectionsystem.rsp;

import lombok.Getter;

/**
 * 通用状态码及其描述
 */
@Getter
public enum REnum {
    /**
     * 通用成功状态
     */
    SUCCESS(200, "成功"),
    /**
     * 通用失败状态
     */
    FAILURE(400, "失败"),
    /**
     * 登录失败状态
     */
    LOGIN_FAILURE(401, "登录失败"),
    /**
     * token非法状态
     */
    TOKEN_ILLEGAL(402, "token非法"),
    /**
     * cookie非法状态
     */
    COOKIE_ILLEGAL(403, "cookie非法"),
    /**
     * 选课失败状态
     */
    SELECT_CLASS_FAILURE(404, "选课失败"),
    /**
     * 删课失败状态
     */
    DELETE_CLASS_FAILURE(405, "退课失败"),
    ;

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 状态描述
     */
    private final String desc;

    REnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
