package cn.ustc.courseselectionsystem;

import cn.ustc.courseselectionsystem.mapper.QueryClassMapper;
import cn.ustc.courseselectionsystem.model.param.QueryClassMapperParam;
import cn.ustc.courseselectionsystem.model.po.ClassPO;
import cn.ustc.courseselectionsystem.model.po.CoursePO;
import cn.ustc.courseselectionsystem.model.po.TeacherPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CourseSelectionSystemApplicationTests {

    @Autowired
    private QueryClassMapper queryClassMapper;

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
