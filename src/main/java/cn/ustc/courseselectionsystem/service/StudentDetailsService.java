package cn.ustc.courseselectionsystem.service;

import cn.ustc.courseselectionsystem.model.vo.LoginRequestVO;
import cn.ustc.courseselectionsystem.model.vo.LoginResponseVO;

public interface StudentDetailsService {
    LoginResponseVO login(LoginRequestVO loginRequestVO);
}
