package cn.ustc.courseselectionsystem.exp;

/**
 * 登录异常
 */
@SuppressWarnings("unused")
public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
