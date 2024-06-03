package cn.ustc.courseselectionsystem;

import cn.ustc.courseselectionsystem.mapper.CourseSelectMapper;
import cn.ustc.courseselectionsystem.mapper.QueryClassMapper;
import cn.ustc.courseselectionsystem.mapper.QueryStudentMapper;
import cn.ustc.courseselectionsystem.mapper.StudentLoginMapper;
import cn.ustc.courseselectionsystem.model.param.QueryClassMapperParam;
import cn.ustc.courseselectionsystem.model.po.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CourseSelectionSystemApplicationTests {

    @Autowired
    private QueryClassMapper queryClassMapper;
    @Autowired
    private StudentLoginMapper studentLoginMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private QueryStudentMapper queryStudentMapper;
    @Autowired
    private CourseSelectMapper courseSelectMapper;

    @Test
    void addUser() {
        StudentLoginPO studentLoginPO = new StudentLoginPO();
        studentLoginPO.setNumber("username");
        studentLoginPO.setPassword(passwordEncoder.encode("password"));
        studentLoginPO.setDepartmentId(12);
        Assert.isTrue(studentLoginMapper.insertStudent(studentLoginPO) == 1, "插入失败");
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testQueryCourseById() {
        CoursePO coursePO = queryClassMapper.queryCourseById(21);
        System.out.println("coursePO = " + coursePO);
    }

    @Test
    void testQueryTeacherByClassId() {
        List<TeacherPO> teachers = queryClassMapper.queryTeacherByClassId(21);
        System.out.println("teachers = " + teachers);
    }

    @Test
    void testQueryClassByParam() {
        QueryClassMapperParam queryClassMapperParam = new QueryClassMapperParam();
        queryClassMapperParam.setDepartmentCode(null);
        queryClassMapperParam.setCourseCode("MATH");
        queryClassMapperParam.setCourseName(null);
        queryClassMapperParam.setTeacherName(null);
        List<ClassPO> classPOS = queryClassMapper.queryClassByParam(queryClassMapperParam);
        System.out.println("classPOS = " + classPOS);
    }

    @Test
    void testQueryStudentInfoByNumber() {
        StudentInfoPO studentLoginPO = queryStudentMapper.queryStudentInfoByNumber("PB23010010");
        System.out.println("studentLoginPO = " + studentLoginPO);
    }

    @Test
    void testQueryLimitCountById() {
        int limitCount = courseSelectMapper.queryLimitCountById(21);
        System.out.println("limitCount = " + limitCount);
    }

    @Test
    void testStreamType() {
        List<ClassPO> classList = courseSelectMapper.queryCheckedClass(1);
        classList.stream().map(ClassPO::getId).flatMap(classId -> {
            List<TpcPO> tpcPOS = queryClassMapper.queryTpcByClassId(classId);
            return tpcPOS.stream();
        }).forEach(System.out::println);
    }

    @Test
    void testStream() {
        int end = 4;
        int start = 1;
        Arrays.stream(new int[end - start + 1]).map(i -> start + i).forEach(System.out::println);
    }

}
