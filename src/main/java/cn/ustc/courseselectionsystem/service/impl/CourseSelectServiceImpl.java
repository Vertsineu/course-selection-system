package cn.ustc.courseselectionsystem.service.impl;

import cn.ustc.courseselectionsystem.exp.DeleteClassException;
import cn.ustc.courseselectionsystem.exp.SelectClassException;
import cn.ustc.courseselectionsystem.mapper.CourseSelectMapper;
import cn.ustc.courseselectionsystem.mapper.QueryClassMapper;
import cn.ustc.courseselectionsystem.mapper.QueryStudentMapper;
import cn.ustc.courseselectionsystem.model.po.ClassPO;
import cn.ustc.courseselectionsystem.model.po.TpcPO;
import cn.ustc.courseselectionsystem.model.vo.CourseVO;
import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;
import cn.ustc.courseselectionsystem.model.vo.TimeCourseListVO;
import cn.ustc.courseselectionsystem.service.CourseSelectService;
import cn.ustc.courseselectionsystem.util.DynamicMapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 选课服务实现类
 */
@Service
@RequiredArgsConstructor
public class CourseSelectServiceImpl implements CourseSelectService {

    /**
     * 选课数据库相关操作类
     */
    private final CourseSelectMapper courseSelectMapper;
    /**
     * 查询学生数据库相关操作类
     */
    private final QueryStudentMapper queryStudentMapper;

    /**
     * 动态工具类
     */
    private final DynamicMapUtil dynamicMapUtil;
    /**
     * 查询课程数据库相关操作类
     */
    private final QueryClassMapper queryClassMapper;

    /**
     * 查询学生已选课程
     * @param username 学生学号
     * @return 学生已选课程列表
     */
    @Transactional(readOnly = true)
    @Override
    public CourseWithClassListVO checkedClasses(String username) {
        Integer id = queryStudentMapper.queryStudentInfoByNumber(username).getId();
        return dynamicMapUtil.mapToCourseWithClassListVO(courseSelectMapper.queryCheckedClass(id), username);
    }

    /**
     * 选择课程，判断是否已选，如果未选，则尝试选择课程，此过程可能存在幻读，因为该事务涉及表项的插入
     * @param classId 课程id
     * @param username 学生学号
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    @Override
    public void selectClass(Integer classId, String username) {
        Integer studentId = queryStudentMapper.queryStudentInfoByNumber(username).getId();

        // 查询是否已选
        int selected = courseSelectMapper.querySelectedById(classId, studentId);
        if (selected != 0)
            throw new SelectClassException("该课程已选，禁止重复选课");

        if (isTimeConflict(classId, studentId))
            throw new SelectClassException("选课时间冲突，无法选课");

        // 查询是否可选，如果可选，则选择课程
        int limitCount = courseSelectMapper.queryLimitCountById(classId);
        int selectedCount = courseSelectMapper.querySelectedCountById(classId);
        if (selectedCount >= limitCount)
            throw new SelectClassException("该课程选课人数已满，无法选课");
        int count = courseSelectMapper.insertClass(classId, studentId);
        if (count == 0)
            throw new SelectClassException("选课失败");
    }

    /**
     * 判断选课时间是否冲突，此过程可能存在幻读，因为该事务涉及表项数目的查询
     * @param classId 要选的课程id
     * @param studentId 学生id
     * @return 是否冲突
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, readOnly = true)
    protected boolean isTimeConflict(Integer classId, Integer studentId) {
        // 查询已选课程的所有时间段
        List<ClassPO> selectedClassesList = courseSelectMapper.queryCheckedClass(studentId);
        List<TpcPO> selectedClassestpcList = selectedClassesList.stream().map(ClassPO::getId).flatMap(selectedClassId -> {
            List<TpcPO> tpcPOS = queryClassMapper.queryTpcByClassId(selectedClassId);
            return tpcPOS.stream();
        }).toList();
        Set<Integer> selectedTimeSet = dynamicMapUtil.mapToTimeSet(selectedClassestpcList);

        // 查询要选课程的所有时间段
        List<TpcPO> classTpcList = queryClassMapper.queryTpcByClassId(classId);
        Set<Integer> timeSet = dynamicMapUtil.mapToTimeSet(classTpcList);

        // 判断是否有时间冲突
        return !Collections.disjoint(selectedTimeSet, timeSet);
    }

    /**
     * 删除已选课程，判断是否已选，如果已选，则尝试删除课程，此过程可能存在幻读，因为该事务涉及表项的删除
     * @param classId 课程id
     * @param username 学生学号
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    @Override
    public void deleteClass(Integer classId, String username) {
        Integer studentId = queryStudentMapper.queryStudentInfoByNumber(username).getId();

        // 查询是否已选
        int selected = courseSelectMapper.querySelectedById(classId, studentId);
        if (selected == 0)
            throw new DeleteClassException("该课程未选，无法删除选课");

        // 尝试删除课程
        int count = courseSelectMapper.deleteClass(classId, studentId);
        if (count == 0)
            throw new DeleteClassException("删除课程失败");
    }

    /**
     * 查询学生已选课程的时间-课程映射列表
     * @param username 学生学号
     * @return 学生已选课程的时间-课程映射列表
     */
    @Override
    public TimeCourseListVO timeSelectedCourseList(String username) {
        Integer studentId = queryStudentMapper.queryStudentInfoByNumber(username).getId();

        TreeMap<Integer, CourseVO> timeCourseMap = new TreeMap<>();
        List<ClassPO> selectedClassesList = courseSelectMapper.queryCheckedClass(studentId);
        selectedClassesList.forEach(classPO -> {
            CourseWithClassListVO courseWithClassListVO = dynamicMapUtil.mapToCourseWithClassListVO(List.of(classPO), username);
            CourseVO courseVO = courseWithClassListVO.getCourseList().get(0);

            List<TpcPO> tpcPOS = queryClassMapper.queryTpcByClassId(classPO.getId());
            Set<Integer> timeSet = dynamicMapUtil.mapToTimeSet(tpcPOS);

            timeSet.forEach(time ->
                    timeCourseMap.put(time, courseVO));
        });

        List<CourseVO> timeCourseList = new ArrayList<>();
        for (int i = 0; i < 20 * 13 * 7; i++)
            timeCourseList.add(timeCourseMap.getOrDefault(i, null));

        return new TimeCourseListVO(timeCourseList);
    }
}
