package cn.ustc.courseselectionsystem.exp;

/**
 * Cookie 非法异常
 */
@SuppressWarnings("unused")
public class CookieIllegalException extends RuntimeException {
    public CookieIllegalException(String message) {
        super(message);
    }

    public CookieIllegalException(String message, Throwable cause) {
        super(message, cause);
    }
}
