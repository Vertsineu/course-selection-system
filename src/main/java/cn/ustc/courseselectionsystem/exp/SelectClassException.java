package cn.ustc.courseselectionsystem.exp;

/**
 * 选课异常
 */
@SuppressWarnings("unused")
public class SelectClassException extends RuntimeException {
    public SelectClassException(String message) {
        super(message);
    }

    public SelectClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
