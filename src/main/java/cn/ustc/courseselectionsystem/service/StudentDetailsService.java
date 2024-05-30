package cn.ustc.courseselectionsystem.service;

import cn.ustc.courseselectionsystem.model.vo.LoginRequestVO;

import java.util.Map;

public interface StudentDetailsService {
    Map<String, Object> login(LoginRequestVO loginRequestVO);
}
