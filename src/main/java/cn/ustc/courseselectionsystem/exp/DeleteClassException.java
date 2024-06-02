package cn.ustc.courseselectionsystem.exp;

public class DeleteClassException extends RuntimeException{
    public DeleteClassException(String message) {
        super(message);
    }

    public DeleteClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
