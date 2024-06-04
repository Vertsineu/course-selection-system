package cn.ustc.courseselectionsystem.exp;

/**
 * 删除课程异常
 */
@SuppressWarnings("unused")
public class DeleteClassException extends RuntimeException{
    public DeleteClassException(String message) {
        super(message);
    }

    public DeleteClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
