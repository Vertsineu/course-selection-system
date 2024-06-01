package cn.ustc.courseselectionsystem.service;

import cn.ustc.courseselectionsystem.model.vo.StudentLoginRequestVO;
import cn.ustc.courseselectionsystem.model.vo.StudentLoginResponseVO;

public interface StudentDetailsService {
    StudentLoginResponseVO login(StudentLoginRequestVO studentLoginRequestVO);
}
