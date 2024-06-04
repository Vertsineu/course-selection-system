package cn.ustc.courseselectionsystem.rsp;

/**
 * 通用返回生成工具类
 */
@SuppressWarnings("unused")
public class RUtils {

    public static <T> R<T> success(String msg, T data) {
        return create(REnum.SUCCESS, msg, data);
    }

    public static <T> R<T> success(String msg) {
        return create(REnum.SUCCESS, msg, null);
    }
    
    public static <T> R<T> success(T data) {
        return create(REnum.SUCCESS, "", data);
    }
   
    public static <T> R<T> success() {
        return create(REnum.SUCCESS, "", null);
    }

    public static <T> R<T> failure(REnum renum, String msg, T data) {
        return create(renum, msg, data);
    }

    public static <T> R<T> failure(REnum renum, String msg) {
        return create(renum, msg, null);
    }

    public static <T> R<T> failure(String msg, T data) {
        return create(REnum.FAILURE, msg, data);
    }

    public static <T> R<T> failure(String msg) {
        return create(REnum.FAILURE, msg, null);
    }

    /**
     * 生成通用返回对象
     * @param renum 状态码及其描述枚举类
     * @param msg 状态信息
     * @param data 返回数据
     * @return 通用返回对象
     * @param <T> 返回数据类型
     */
    public static <T> R<T> create(REnum renum, String msg, T data) {
        R<T> r = new R<>();
        r.setCode(renum.getCode());
        r.setDesc(renum.getDesc());
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}
