package cn.ustc.courseselectionsystem.exp.handler;

import cn.ustc.courseselectionsystem.exp.*;
import cn.ustc.courseselectionsystem.rsp.None;
import cn.ustc.courseselectionsystem.rsp.R;
import cn.ustc.courseselectionsystem.rsp.REnum;
import cn.ustc.courseselectionsystem.rsp.RUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理登录异常
     * @param e 登录异常
     * @return 登陆失败
     */
    @ExceptionHandler({LoginException.class, UsernameNotFoundException.class})
    public R<None> handleLoginException(LoginException e) {
        return RUtils.failure(REnum.LOGIN_FAILURE, e.getMessage());
    }

    /**
     * 处理 Token 非法异常
     * @param e Token 非法异常
     * @return Token 非法
     */
    @ExceptionHandler({TokenIllegalException.class})
    public R<None> handleTokenIllegalException(TokenIllegalException e) {
        return RUtils.failure(REnum.TOKEN_ILLEGAL, e.getMessage());
    }

    /**
     * 处理 Cookie 非法异常
     * @param e Cookie 非法异常
     * @return Cookie 非法
     */
    @ExceptionHandler({CookieIllegalException.class})
    public R<None> handleCookieIllegalException(CookieIllegalException e) {
        return RUtils.failure(REnum.COOKIE_ILLEGAL, e.getMessage());
    }

    /**
     * 处理删除课程异常
     * @param e 删除课程异常
     * @return 删除课程失败
     */
    @ExceptionHandler({DeleteClassException.class})
    public R<None> handleDeleteClassException(DeleteClassException e) {
        return RUtils.failure(REnum.DELETE_CLASS_FAILURE, e.getMessage());
    }

    /**
     * 处理选课异常
     * @param e 选课异常
     * @return 选课失败
     */
    @ExceptionHandler({SelectClassException.class})
    public R<None> handleSelectClassException(SelectClassException e) {
        return RUtils.failure(REnum.SELECT_CLASS_FAILURE, e.getMessage());
    }

    /**
     * 处理剩余异常
     * @param e 剩余异常
     * @return 失败
     */
    @ExceptionHandler({Exception.class})
    public R<None> handleException(Exception e) {
        return RUtils.failure(e.getMessage());
    }


}
