package cn.ustc.courseselectionsystem.exp.handler;

import cn.ustc.courseselectionsystem.exp.CookieIllegalException;
import cn.ustc.courseselectionsystem.exp.LoginException;
import cn.ustc.courseselectionsystem.exp.TokenIllegalException;
import cn.ustc.courseselectionsystem.rsp.None;
import cn.ustc.courseselectionsystem.rsp.R;
import cn.ustc.courseselectionsystem.rsp.REnum;
import cn.ustc.courseselectionsystem.rsp.RUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({LoginException.class, UsernameNotFoundException.class})
    public R<None> handleLoginException(LoginException e) {
        return RUtils.failure(REnum.LOGIN_FAILURE, e.getMessage());
    }

    @ExceptionHandler({TokenIllegalException.class})
    public R<None> handleTokenIllegalException(TokenIllegalException e) {
        return RUtils.failure(REnum.TOKEN_ILLEGAL, e.getMessage());
    }

    @ExceptionHandler({CookieIllegalException.class})
    public R<None> handleCookieIllegalException(CookieIllegalException e) {
        return RUtils.failure(REnum.COOKIE_ILLEGAL, e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public R<None> handleException(Exception e) {
        return RUtils.failure(e.getMessage());
    }


}
