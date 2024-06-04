package cn.ustc.courseselectionsystem.exp;

/**
 * Token 非法异常
 */
@SuppressWarnings("unused")
public class TokenIllegalException extends RuntimeException {
    public TokenIllegalException(String message) {
        super(message);
    }

    public TokenIllegalException(String message, Throwable cause) {
        super(message, cause);
    }
}
