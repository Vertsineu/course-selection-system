package cn.ustc.courseselectionsystem.controller;

import cn.ustc.courseselectionsystem.model.vo.LoginRequestVO;
import cn.ustc.courseselectionsystem.model.vo.LoginResponseVO;
import cn.ustc.courseselectionsystem.service.StudentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class LoginController {

    private final StudentDetailsService studentDetailsService;

    @GetMapping("/login")
    public LoginResponseVO login(@RequestBody LoginRequestVO loginRequestVO) {
        return studentDetailsService.login(loginRequestVO);
    }

    @GetMapping("/info")
    public String info() {
        return "Hello World!";
    }

}
