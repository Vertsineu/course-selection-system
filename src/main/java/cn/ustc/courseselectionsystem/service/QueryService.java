package cn.ustc.courseselectionsystem.service;

import cn.ustc.courseselectionsystem.model.param.QueryClassParam;
import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;
import cn.ustc.courseselectionsystem.model.vo.StudentInfoVO;

public interface QueryService {
    CourseWithClassListVO forClass(QueryClassParam queryClassParam, String username);
    StudentInfoVO forStudentInfo(String username);
}
