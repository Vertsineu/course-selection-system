package cn.ustc.courseselectionsystem.service;

import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;

public interface CourseSelectService {
    CourseWithClassListVO checkedClasses(String username);

    void selectClass(Integer classId, String username);

    void deleteClass(Integer classId, String username);
}
