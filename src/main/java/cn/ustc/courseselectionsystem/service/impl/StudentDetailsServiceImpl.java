package cn.ustc.courseselectionsystem.service.impl;

import cn.ustc.courseselectionsystem.config.security.Student;
import cn.ustc.courseselectionsystem.model.vo.LoginRequestVO;
import cn.ustc.courseselectionsystem.service.StudentDetailsService;
import cn.ustc.courseselectionsystem.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentDetailsServiceImpl implements StudentDetailsService {

    private final AuthenticationManager authenticationManager;

    private final TokenUtil tokenUtil;

    public Map<String, Object> login(LoginRequestVO loginRequestVO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequestVO.getUsername(), loginRequestVO.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误");
        }

        Student student = (Student) authentication.getPrincipal();
        String token = tokenUtil.createToken(student.getUsername(), student.getPassword());

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);

        return map;
    }
}
