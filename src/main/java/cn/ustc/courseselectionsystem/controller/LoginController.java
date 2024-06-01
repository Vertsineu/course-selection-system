package cn.ustc.courseselectionsystem.controller;

import cn.ustc.courseselectionsystem.exp.LoginException;
import cn.ustc.courseselectionsystem.model.vo.LoginRequestVO;
import cn.ustc.courseselectionsystem.model.vo.LoginResponseVO;
import cn.ustc.courseselectionsystem.rsp.R;
import cn.ustc.courseselectionsystem.rsp.REnum;
import cn.ustc.courseselectionsystem.rsp.RUtils;
import cn.ustc.courseselectionsystem.service.StudentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class LoginController {

    private final StudentDetailsService studentDetailsService;

    @PostMapping("/login")
    public R<LoginResponseVO> login(@RequestBody LoginRequestVO loginRequestVO) {
        try {
            LoginResponseVO loginResponseVO = studentDetailsService.login(loginRequestVO);
            return RUtils.success(loginResponseVO);
        } catch (LoginException e) {
            return RUtils.failure(REnum.LOGIN_FAILURE, e.getMessage());
        } 
        catch (Exception e) {
            return RUtils.failure(e.getMessage());
        }
    }

    @GetMapping("/info")
    public String info() {
        return "Hello World!";
    }

}
