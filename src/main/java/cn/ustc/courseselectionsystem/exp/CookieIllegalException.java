package cn.ustc.courseselectionsystem.exp;

@SuppressWarnings("unused")
public class CookieIllegalException extends RuntimeException {
    public CookieIllegalException(String message) {
        super(message);
    }

    public CookieIllegalException(String message, Throwable cause) {
        super(message, cause);
    }
}
