package cn.ustc.courseselectionsystem.rsp;

import lombok.Getter;

@Getter
public enum REnum {
    SUCCESS(200, "成功"),
    FAILURE(400, "失败"),
    LOGIN_FAILURE(401, "登录失败"),
    ;

    private Integer code;
    private String desc;

    REnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
