package cn.ustc.courseselectionsystem.service.impl;

import cn.ustc.courseselectionsystem.config.security.Student;
import cn.ustc.courseselectionsystem.exp.LoginException;
import cn.ustc.courseselectionsystem.model.vo.StudentLoginRequestVO;
import cn.ustc.courseselectionsystem.model.vo.StudentLoginResponseVO;
import cn.ustc.courseselectionsystem.service.StudentDetailsService;
import cn.ustc.courseselectionsystem.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentDetailsServiceImpl implements StudentDetailsService {

    private final AuthenticationManager authenticationManager;

    private final TokenUtil tokenUtil;

    public StudentLoginResponseVO login(StudentLoginRequestVO studentLoginRequestVO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(studentLoginRequestVO.getUsername(), studentLoginRequestVO.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (Objects.isNull(authentication)) {
            throw new LoginException("用户名或密码错误");
        }

        Student student = (Student) authentication.getPrincipal();
        String token = tokenUtil.createToken(student.getUsername(), student.getPassword());

        StudentLoginResponseVO studentLoginResponseVO = new StudentLoginResponseVO();
        studentLoginResponseVO.setToken(token);

        return studentLoginResponseVO;
    }

}
