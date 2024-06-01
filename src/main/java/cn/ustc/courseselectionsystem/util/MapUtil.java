package cn.ustc.courseselectionsystem.util;

import cn.ustc.courseselectionsystem.model.param.QueryClassMapperParam;
import cn.ustc.courseselectionsystem.model.param.QueryClassParam;
import cn.ustc.courseselectionsystem.model.po.ClassPO;
import cn.ustc.courseselectionsystem.model.po.CoursePO;
import cn.ustc.courseselectionsystem.model.po.DepartmentPO;
import cn.ustc.courseselectionsystem.model.tuple.DepartmentTeachersTpcTuple;
import cn.ustc.courseselectionsystem.model.vo.ClassVO;
import cn.ustc.courseselectionsystem.model.vo.CourseVO;
import cn.ustc.courseselectionsystem.model.vo.DepartmentVO;

import java.util.List;

public class MapUtil {
    public static QueryClassMapperParam mapToQueryClassMapperParam(QueryClassParam queryClassParam) {
        QueryClassMapperParam queryClassMapperParam = new QueryClassMapperParam();
        queryClassMapperParam.setDepartmentCode(queryClassParam.getDepartmentCode());
        queryClassMapperParam.setCourseCode(queryClassParam.getCourseCode());
        queryClassMapperParam.setCourseName(queryClassParam.getCourseName());
        queryClassMapperParam.setTeacherName(queryClassParam.getTeacherName());
        return queryClassMapperParam;
    }

    public static DepartmentVO mapToDepartmentPO(DepartmentPO departmentPO) {
        DepartmentVO departmentVO = new DepartmentVO();
        departmentVO.setCode(departmentPO.getCode());
        departmentVO.setName(departmentPO.getName());
        departmentVO.setCollege(departmentPO.getCollege());
        return departmentVO;
    }

    public static ClassVO mapToClassVO(ClassPO classPO, DepartmentTeachersTpcTuple tuple) {
        ClassVO classVO = new ClassVO();
        classVO.setCode(classPO.getCode());
        classVO.setCampus(classPO.getCampus());
        classVO.setType(classPO.getType());
        classVO.setExamMode(classPO.getExamMode());
        classVO.setGraduateAndPostGraduate(classPO.getGraduateAndPostGraduate());
        classVO.setGraduateAndPostGraduate(classPO.getGraduateAndPostGraduate());
        classVO.setPeriodPerWeek(classPO.getPeriodPerWeek());
        classVO.setLimitCount(classPO.getLimitCount());
        classVO.setTeachLang(classPO.getTeachLang());
        classVO.setDepartment(mapToDepartmentPO(tuple.getDepartment()));
        classVO.setTpc(tuple.getTpc());
        classVO.setTeachers(tuple.getTeachers());

        return classVO;
    }

    public static CourseVO mapToCourseVO(CoursePO coursePO, List<ClassVO> classVOList) {
        CourseVO courseVO = new CourseVO();
        courseVO.setCode(coursePO.getCode());
        courseVO.setName(coursePO.getName());
        courseVO.setCategory(coursePO.getCategory());
        courseVO.setClassify(coursePO.getClassify());
        courseVO.setGradation(coursePO.getGradation());
        courseVO.setType(coursePO.getType());
        courseVO.setCredits(coursePO.getCredits());
        courseVO.setEducation(coursePO.getEducation());
        courseVO.setPeriodTotal(coursePO.getPeriodTotal());
        courseVO.setClasses(classVOList);
        return courseVO;
    }

}