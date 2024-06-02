package cn.ustc.courseselectionsystem.util;

import cn.ustc.courseselectionsystem.mapper.CourseSelectMapper;
import cn.ustc.courseselectionsystem.mapper.QueryClassMapper;
import cn.ustc.courseselectionsystem.mapper.QueryStudentMapper;
import cn.ustc.courseselectionsystem.model.po.*;
import cn.ustc.courseselectionsystem.model.tuple.DepartmentTeachersTpcTuple;
import cn.ustc.courseselectionsystem.model.tuple.SelectStateTuple;
import cn.ustc.courseselectionsystem.model.vo.ClassVO;
import cn.ustc.courseselectionsystem.model.vo.CourseVO;
import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DynamicMapUtil {

    private final QueryClassMapper queryClassMapper;
    private final CourseSelectMapper courseSelectMapper;
    private final QueryStudentMapper queryStudentMapper;

    public CourseWithClassListVO mapToCourseWithClassListVO(List<ClassPO> classList, String username) {
        // 将课堂信息存储到 map 中
        Map<CoursePO, List<ClassPO>> courseWithClassMap = new HashMap<>();
        Map<ClassPO, DepartmentTeachersTpcTuple> classWithDepartmentTeachersTpcMap = new HashMap<>();

        classList.forEach(classPO -> {
            // 找到所有课堂所属的课程，部门，教师，时间地点人物
            CoursePO coursePO = queryClassMapper.queryCourseById(classPO.getCourseId());
            DepartmentPO department = queryClassMapper.queryDepartmentById(classPO.getDepartmentId());
            List<TeacherPO> teacherList = queryClassMapper.queryTeacherByClassId(classPO.getId());
            String teachers = teacherList.stream().map(TeacherPO::getName).reduce((s1, s2) -> s1 + ',' + s2).orElse("");

            List<TpcPO> tpcList = queryClassMapper.queryTpcByClassId(classPO.getId());
            String tpc = tpcList.stream()
                    .map(tpcPO -> tpcPO.getTimeWeek() + ' ' + tpcPO.getPlace() + ' ' + ':' + tpcPO.getTimeDay() + '(' + tpcPO.getTimePeriod() + ')' + ' ' + queryClassMapper.queryTeacherById(tpcPO.getTeacherId()).getName())
                    .reduce((s1, s2) -> s1 + '\n' + s2).orElse("");

            // 添加进 courseWithClassMap
            if (!courseWithClassMap.containsKey(coursePO))
                courseWithClassMap.put(coursePO, new ArrayList<>());
            courseWithClassMap.get(coursePO).add(classPO);

            // 添加进 classWithDepartmentTeachersTpcMap
            classWithDepartmentTeachersTpcMap.put(classPO, new DepartmentTeachersTpcTuple(teachers, tpc, department));
        });

        // 将 map 转换为 list
        List<CourseVO> courseVOList = new ArrayList<>();

        courseWithClassMap.forEach((coursePO, classPOList) -> {
            List<ClassVO> list = classPOList.stream().map(classPO -> {
                DepartmentTeachersTpcTuple tuple = classWithDepartmentTeachersTpcMap.get(classPO);
                Integer selectedCount = courseSelectMapper.querySelectedCountById(classPO.getId());
                Integer studentId = queryStudentMapper.queryStudentInfoByNumber(username).getId();
                int selected = courseSelectMapper.querySelectedById(classPO.getId(), studentId);
                return MapUtil.mapToClassVO(classPO, tuple, new SelectStateTuple(selectedCount, selected > 0));
            }).toList();
            CourseVO courseVO = MapUtil.mapToCourseVO(coursePO, list);
            courseVOList.add(courseVO);
        });

        return new CourseWithClassListVO(courseVOList);
    }
}
