package cn.ustc.courseselectionsystem.mapper;

import cn.ustc.courseselectionsystem.model.param.QueryClassMapperParam;
import cn.ustc.courseselectionsystem.model.po.*;
import cn.ustc.courseselectionsystem.sql.SqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 查询课程相关的数据库操作
 */
@Mapper
public interface QueryClassMapper {

    /**
     * 查询课程
     * @param param 查询参数
     * @return 课程列表
     */
    @SelectProvider(type = SqlProvider.class, method = "queryClassByParam")
    @Results(id = "classMap", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property= "code", column = "code"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "departmentId", column = "department_id"),
        @Result(property = "campus", column = "campus_cn"),
        @Result(property = "type", column = "type_cn"),
        @Result(property = "examMode", column = "exam_mode_cn"),
        @Result(property = "graduateAndPostGraduate", column = "graduate_and_post_graduate"),
        @Result(property = "periodPerWeek", column = "period_per_week"),
        @Result(property = "limitCount", column = "limit_count"),
        @Result(property = "teachLang", column = "teach_lang_cn")
    })
    List<ClassPO> queryClassByParam(QueryClassMapperParam param);

    /**
     * 查询课程
     * @param id 课程id
     * @return 课程
     */
    @Select("""
           select id, code, name_cn, category_cn , classify_cn , gradation_cn , type_cn , credits, education_cn, period_total
           from tbl_course
           where id = #{id}
           """)
    @Results(id = "courseMap", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "code", column = "code"),
        @Result(property = "name", column = "name_cn"),
        @Result(property = "category", column = "category_cn"),
        @Result(property = "classify", column = "classify_cn"),
        @Result(property = "gradation", column = "gradation_cn"),
        @Result(property = "type", column = "type_cn"),
        @Result(property = "credits", column = "credits"),
        @Result(property = "education", column = "education_cn"),
        @Result(property = "periodTotal", column = "period_total")
    })
    CoursePO queryCourseById(Integer id);

    /**
     * 查询部门
     * @param id 部门id
     * @return 部门
     */
    @Select("""
            select id, code, name_cn, college
            from tbl_department
            where id = #{id}
            """)
    @Results(id = "departmentMap", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "code", column = "code"),
        @Result(property = "name", column = "name_cn"),
        @Result(property = "college", column = "college")
    })
    DepartmentPO queryDepartmentById(Integer id);

    /**
     * 查询时间地点人物
     * @param classId 课程id
     * @return 时间地点人物
     */
    @Select("""
            select class_id, time_week, time_day, time_period, place, teacher_id
            from tbl_tpc
            where class_id = #{classId}
          """)
    @Results(id = "tpcMap", value = {
        @Result(property = "classId", column = "class_id", id = true),
        @Result(property = "timeWeek", column = "time_week"),
        @Result(property = "timeDay", column = "time_day"),
        @Result(property = "timePeriod", column = "time_period"),
        @Result(property = "place", column = "place"),
        @Result(property = "teacherId", column = "teacher_id")
    })
    List<TpcPO> queryTpcByClassId(Integer classId);

    /**
     * 查询教师
     * @param classId 课程id
     * @return 教师
     */
    @Select("""
            select tc.teacher_id, t.department_id, t.name_cn
            from tbl_teacher_class tc
            left join tbl_teacher t on tc.teacher_id = t.id
            where tc.class_id = #{classId}
            """)
    @Results(id = "teacherMap", value = {
        @Result(property = "id", column = "teacher_id", id = true),
        @Result(property = "departmentId", column = "department_id"),
        @Result(property = "name", column = "name_cn")
    })
    List<TeacherPO> queryTeacherByClassId(Integer classId);

    /**
     * 查询教师
     * @param teacherId 教师id
     * @return 教师
     */
    @Select("""
            select id, department_id, name_cn
            from tbl_teacher
            where id = #{id}
            """)
    @ResultMap("teacherMap")
    TeacherPO queryTeacherById(Integer teacherId);

}
