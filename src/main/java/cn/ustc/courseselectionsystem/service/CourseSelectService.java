package cn.ustc.courseselectionsystem.service;

import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;
import cn.ustc.courseselectionsystem.model.vo.TimeCourseListVO;

public interface CourseSelectService {
    CourseWithClassListVO checkedClasses(String username);

    void selectClass(Integer classId, String username);

    void deleteClass(Integer classId, String username);

    TimeCourseListVO timeSelectedCourseList(String username);
}
