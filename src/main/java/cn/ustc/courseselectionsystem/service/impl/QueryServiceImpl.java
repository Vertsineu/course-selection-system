package cn.ustc.courseselectionsystem.service.impl;

import cn.ustc.courseselectionsystem.mapper.QueryClassMapper;
import cn.ustc.courseselectionsystem.mapper.QueryStudentMapper;
import cn.ustc.courseselectionsystem.model.param.QueryClassMapperParam;
import cn.ustc.courseselectionsystem.model.param.QueryClassParam;
import cn.ustc.courseselectionsystem.model.po.*;
import cn.ustc.courseselectionsystem.model.tuple.DepartmentTeachersTpcTuple;
import cn.ustc.courseselectionsystem.model.vo.ClassVO;
import cn.ustc.courseselectionsystem.model.vo.CourseVO;
import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;
import cn.ustc.courseselectionsystem.model.vo.StudentInfoVO;
import cn.ustc.courseselectionsystem.service.QueryService;
import cn.ustc.courseselectionsystem.util.MapUtil;
import cn.ustc.courseselectionsystem.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private final QueryClassMapper queryClassMapper;
    private final QueryStudentMapper queryStudentMapper;
    private final TokenUtil tokenUtil;

    @Transactional(readOnly = true)
    @Override
    public CourseWithClassListVO forClass(QueryClassParam queryClassParam) {
        QueryClassMapperParam param = MapUtil.mapToQueryClassMapperParam(queryClassParam);
        // 获取所有满足参数条件的课堂
        List<ClassPO> classList = queryClassMapper.queryClassByParam(param);

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
                return MapUtil.mapToClassVO(classPO, tuple);
            }).toList();
            CourseVO courseVO = MapUtil.mapToCourseVO(coursePO, list);
            courseVOList.add(courseVO);
        });

        return new CourseWithClassListVO(courseVOList);
    }

    @Transactional(readOnly = true)
    @Override
    public StudentInfoVO forStudentInfo(String token) {
        String username = tokenUtil.parseToken(token).get("username");
        StudentInfoPO studentInfoPO = queryStudentMapper.queryStudentInfoByNumber(username);
        return MapUtil.mapToStudentInfoVO(studentInfoPO);
    }
}
