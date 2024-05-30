package cn.ustc.courseselectionsystem.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return null;
    }    
}
