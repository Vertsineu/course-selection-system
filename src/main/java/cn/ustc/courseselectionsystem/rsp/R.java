package cn.ustc.courseselectionsystem.rsp;

import lombok.Data;

@Data
public class R<T> {
    private Integer code;
    private String desc;
    private String msg;
    private T data;
}
