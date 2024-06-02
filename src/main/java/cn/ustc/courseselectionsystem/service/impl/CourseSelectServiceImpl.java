package cn.ustc.courseselectionsystem.service.impl;

import cn.ustc.courseselectionsystem.exp.DeleteClassException;
import cn.ustc.courseselectionsystem.exp.SelectClassException;
import cn.ustc.courseselectionsystem.mapper.CourseSelectMapper;
import cn.ustc.courseselectionsystem.mapper.QueryStudentMapper;
import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;
import cn.ustc.courseselectionsystem.service.CourseSelectService;
import cn.ustc.courseselectionsystem.util.DynamicMapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseSelectServiceImpl implements CourseSelectService {

    private final CourseSelectMapper courseSelectMapper;
    private final QueryStudentMapper queryStudentMapper;

    private final DynamicMapUtil dynamicMapUtil;

    @Transactional(readOnly = true)
    @Override
    public CourseWithClassListVO checkedClasses(String username) {
        Integer id = queryStudentMapper.queryStudentInfoByNumber(username).getId();
        return dynamicMapUtil.mapToCourseWithClassListVO(courseSelectMapper.queryCheckedClass(id), username);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void selectClass(Integer classId, String username) {
        Integer studentId = queryStudentMapper.queryStudentInfoByNumber(username).getId();
        int selected = courseSelectMapper.querySelectedById(classId, studentId);
        if (selected != 0)
            throw new SelectClassException("该课程已选，禁止重复选课");
        int count = courseSelectMapper.insertClass(classId, studentId);
        if (count == 0)
            throw new SelectClassException("选课失败");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteClass(Integer classId, String username) {
        Integer studentId = queryStudentMapper.queryStudentInfoByNumber(username).getId();
        int selected = courseSelectMapper.querySelectedById(classId, studentId);
        if (selected == 0)
            throw new DeleteClassException("该课程未选，无法删除选课");
        int count = courseSelectMapper.deleteClass(classId, studentId);
        if (count == 0)
            throw new DeleteClassException("删除课程失败");
    }
}
