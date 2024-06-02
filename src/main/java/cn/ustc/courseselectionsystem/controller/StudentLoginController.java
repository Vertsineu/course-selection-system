package cn.ustc.courseselectionsystem.controller;

import cn.ustc.courseselectionsystem.model.vo.StudentLoginRequestVO;
import cn.ustc.courseselectionsystem.model.vo.StudentLoginResponseVO;
import cn.ustc.courseselectionsystem.rsp.R;
import cn.ustc.courseselectionsystem.rsp.RUtils;
import cn.ustc.courseselectionsystem.service.StudentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class StudentLoginController {

    private final StudentDetailsService studentDetailsService;

    @PostMapping("/login")
    public R<StudentLoginResponseVO> login(@RequestBody StudentLoginRequestVO studentLoginRequestVO) {
        StudentLoginResponseVO studentLoginResponseVO = studentDetailsService.login(studentLoginRequestVO);
        return RUtils.success(studentLoginResponseVO);
    }

}
