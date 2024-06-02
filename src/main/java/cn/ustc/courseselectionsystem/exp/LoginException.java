package cn.ustc.courseselectionsystem.exp;

@SuppressWarnings("unused")
public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
