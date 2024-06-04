package cn.ustc.courseselectionsystem.util;

import cn.ustc.courseselectionsystem.model.param.QueryClassMapperParam;
import cn.ustc.courseselectionsystem.model.param.QueryClassParam;
import cn.ustc.courseselectionsystem.model.po.ClassPO;
import cn.ustc.courseselectionsystem.model.po.CoursePO;
import cn.ustc.courseselectionsystem.model.po.DepartmentPO;
import cn.ustc.courseselectionsystem.model.po.StudentInfoPO;
import cn.ustc.courseselectionsystem.model.tuple.DepartmentTeachersTpcTuple;
import cn.ustc.courseselectionsystem.model.tuple.SelectStateTuple;
import cn.ustc.courseselectionsystem.model.vo.ClassVO;
import cn.ustc.courseselectionsystem.model.vo.CourseVO;
import cn.ustc.courseselectionsystem.model.vo.DepartmentVO;
import cn.ustc.courseselectionsystem.model.vo.StudentInfoVO;

import java.util.List;

/**
 * 静态映射工具类
 */
public class MapUtil {
    /**
     * 将查询课程参数映射为查询课程映射参数
     * @param queryClassParam 查询课程参数
     * @return 查询课程映射参数
     */
    public static QueryClassMapperParam mapToQueryClassMapperParam(QueryClassParam queryClassParam) {
        QueryClassMapperParam queryClassMapperParam = new QueryClassMapperParam();
        queryClassMapperParam.setDepartmentCode(queryClassParam.getDepartmentCode());
        queryClassMapperParam.setCourseCode(queryClassParam.getCourseCode());
        queryClassMapperParam.setCourseName(queryClassParam.getCourseName());
        queryClassMapperParam.setTeacherName(queryClassParam.getTeacherName());
        return queryClassMapperParam;
    }

    /**
     * 将部门持久化对象映射为部门值对象
     * @param departmentPO 部门持久化对象
     * @return 部门值对象
     */
    public static DepartmentVO mapToDepartmentPO(DepartmentPO departmentPO) {
        DepartmentVO departmentVO = new DepartmentVO();
        departmentVO.setId(departmentPO.getId());
        departmentVO.setCode(departmentPO.getCode());
        departmentVO.setName(departmentPO.getName());
        departmentVO.setCollege(departmentPO.getCollege());
        return departmentVO;
    }

    /**
     * 将课堂持久化对象映射为课堂值对象
     * @param classPO 课堂持久化对象
     * @param tuple 部门，教师，时间地点人物类
     * @param selectStateTuple 选课状态类
     * @return 课堂值对象
     */
    public static ClassVO mapToClassVO(ClassPO classPO, DepartmentTeachersTpcTuple tuple, SelectStateTuple selectStateTuple) {
        ClassVO classVO = new ClassVO();
        classVO.setId(classPO.getId());
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
        classVO.setSelectedCount(selectStateTuple.getSelectedCount());
        classVO.setSelected(selectStateTuple.getSelected());

        return classVO;
    }

    /**
     * 将课程持久化对象映射为课程值对象
     * @param coursePO 课程持久化对象
     * @param classVOList 课堂值对象列表
     * @return 课程值对象
     */
    public static CourseVO mapToCourseVO(CoursePO coursePO, List<ClassVO> classVOList) {
        CourseVO courseVO = new CourseVO();
        courseVO.setId(coursePO.getId());
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

    /**
     * 将学生持久化对象映射为学生值对象
     * @param studentInfoPO 学生持久化对象
     * @return 学生值对象
     */
    public static StudentInfoVO mapToStudentInfoVO(StudentInfoPO studentInfoPO) {
        StudentInfoVO studentInfoVO = new StudentInfoVO();
        studentInfoVO.setId(studentInfoPO.getId());
        studentInfoVO.setNumber(studentInfoPO.getNumber());
        studentInfoVO.setName(studentInfoPO.getName());
        studentInfoVO.setDepartment(mapToDepartmentPO(studentInfoPO.getDepartment()));
        return studentInfoVO;
    }
}
