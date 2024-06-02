package cn.ustc.courseselectionsystem.exp;

@SuppressWarnings("unused")
public class SelectClassException extends RuntimeException {
    public SelectClassException(String message) {
        super(message);
    }

    public SelectClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
