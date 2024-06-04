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

/**
 * 学生登录控制层
 */
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class StudentLoginController {

    /**
     * 学生详细信息服务层
     */
    private final StudentDetailsService studentDetailsService;

    /**
     * 学生登录
     * @param studentLoginRequestVO 学生登录请求
     * @return 学生登录响应
     */
    @PostMapping("/login")
    public R<StudentLoginResponseVO> login(@RequestBody StudentLoginRequestVO studentLoginRequestVO) {
        StudentLoginResponseVO studentLoginResponseVO = studentDetailsService.login(studentLoginRequestVO);
        return RUtils.success(studentLoginResponseVO);
    }

}
