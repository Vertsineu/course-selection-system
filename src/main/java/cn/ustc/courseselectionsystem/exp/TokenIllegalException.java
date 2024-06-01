package cn.ustc.courseselectionsystem.exp;

@SuppressWarnings("unused")
public class TokenIllegalException extends RuntimeException {
    public TokenIllegalException(String message) {
        super(message);
    }

    public TokenIllegalException(String message, Throwable cause) {
        super(message, cause);
    }
}
