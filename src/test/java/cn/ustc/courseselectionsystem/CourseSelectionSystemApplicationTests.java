package cn.ustc.courseselectionsystem;

import cn.ustc.courseselectionsystem.mapper.QueryClassMapper;
import cn.ustc.courseselectionsystem.mapper.StudentLoginMapper;
import cn.ustc.courseselectionsystem.model.param.QueryClassMapperParam;
import cn.ustc.courseselectionsystem.model.po.ClassPO;
import cn.ustc.courseselectionsystem.model.po.CoursePO;
import cn.ustc.courseselectionsystem.model.po.StudentLoginPO;
import cn.ustc.courseselectionsystem.model.po.TeacherPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class CourseSelectionSystemApplicationTests {

    @Autowired
    private QueryClassMapper queryClassMapper;
    @Autowired
    private StudentLoginMapper studentLoginMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

}
