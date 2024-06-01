package cn.ustc.courseselectionsystem.service;

import cn.ustc.courseselectionsystem.model.param.QueryClassParam;
import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;

public interface QueryClassService {
    CourseWithClassListVO forClass(QueryClassParam queryClassParam);
}
