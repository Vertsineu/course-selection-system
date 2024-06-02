package cn.ustc.courseselectionsystem.rsp;

import lombok.Getter;

@Getter
public enum REnum {
    SUCCESS(200, "成功"),
    FAILURE(400, "失败"),
    LOGIN_FAILURE(401, "登录失败"),
    TOKEN_ILLEGAL(402, "token非法"),
    COOKIE_ILLEGAL(403, "cookie非法"),
    SELECT_CLASS_FAILURE(404, "选课失败"),
    DELETE_CLASS_FAILURE(405, "退课失败"),
    ;

    private final Integer code;
    private final String desc;

    REnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
