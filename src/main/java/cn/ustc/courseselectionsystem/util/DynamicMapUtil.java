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

import java.util.*;
import java.util.stream.Collectors;

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
            String teachers = teacherList.stream()
                    .map(TeacherPO::getName)
                    .reduce(this::teachersReduce).orElse("");

            List<TpcPO> tpcList = queryClassMapper.queryTpcByClassId(classPO.getId());
            String tpc = tpcList.stream()
                    .map(this::tpcFormat)
                    .reduce(this::tpcStrReduce).orElse("");

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

    private String teachersReduce(String t1, String t2) {
        return t1 + ',' + t2;
    }

    private String tpcFormat(TpcPO tpcPO) {
        return tpcPO.getTimeWeek()
                .replaceAll("-", "~")
                .replaceAll("\\(0\\)", "(双)")
                .replaceAll("\\(1\\)", "(单)") +
                '周' +
                ' ' +
                tpcPO.getPlace() +
                ' ' +
                ':' +
                tpcPO.getTimeDay() +
                '(' +
                tpcPO.getTimePeriod() +
                ')' +
                ' ' +
                queryClassMapper.queryTeacherById(tpcPO.getTeacherId()).getName();
    }

    private String tpcStrReduce(String tpc1, String tpc2) {
        return tpc1 + '\n' + tpc2;
    }

    public Set<Integer> mapToTimeSet(List<TpcPO> tpcList) {
        // 所有时间
        Set<Integer> timeSet = new TreeSet<>();

        // 判断时间是否冲突
        tpcList.forEach(tpcPO -> {
            // 获取时间
            String timeWeek = tpcPO.getTimeWeek();
            String timeDay = tpcPO.getTimeDay();
            String timePeriod = tpcPO.getTimePeriod();

            // 解析 week
            Set<Integer> weekSet = new HashSet<>();

            Arrays.stream(timeWeek.split(",")).forEach(week -> {
                if (week.endsWith("(0)")) {
                    week = week.replace("(0)", "");
                    if (!week.contains("-"))
                        weekSet.add(Integer.parseInt(week));
                    else {
                        var split = week.split("-");
                        int start = Integer.parseInt(split[0]);
                        int end = Integer.parseInt(split[1]);
                        for (int i = start + start % 2; i < end; i++)
                            weekSet.add(i);
                    }
                } else if (week.endsWith("(1)")) {
                    week = week.replace("(1)", "");
                    if (!week.contains("-"))
                        weekSet.add(Integer.parseInt(week));
                    else {
                        var split = week.split("-");
                        int start = Integer.parseInt(split[0]);
                        int end = Integer.parseInt(split[1]);
                        for (int i = start + 1 - start % 2; i < end; i++)
                            weekSet.add(i);
                    }
                } else {
                    if (!week.contains("-"))
                        weekSet.add(Integer.parseInt(week));
                    else {
                        var split = week.split("-");
                        int start = Integer.parseInt(split[0]);
                        int end = Integer.parseInt(split[1]);
                        for (int i = start; i <= end; i++)
                            weekSet.add(i);
                    }
                }
            });

            // 解析 day
            int day = Integer.parseInt(timeDay);

            // 解析 period
            Set<Integer> periodSet = Arrays.stream(timePeriod.split(","))
                    .filter(str -> str.matches("\\d+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());

            // 添加进 selectedTimeSet，格式为 (week - 1) * 13 * 7 + (day - 1) * 13 + (period - 1)
            weekSet.forEach(week ->
                    periodSet.forEach(period ->
                            timeSet.add((week - 1) * 13 * 7 + (day - 1) * 13 + (period - 1))));

        });

        return timeSet;
    }
}
