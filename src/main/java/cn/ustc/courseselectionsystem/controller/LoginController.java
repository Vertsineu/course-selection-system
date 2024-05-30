package cn.ustc.courseselectionsystem.controller;

import cn.ustc.courseselectionsystem.model.vo.LoginRequestVO;
import cn.ustc.courseselectionsystem.service.StudentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class LoginController {

    private final StudentDetailsService studentDetailsService;

    @GetMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequestVO loginRequestVO) {
        return studentDetailsService.login(loginRequestVO);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/info")
    public String info() {
        return "Hello World!";
    }

}
