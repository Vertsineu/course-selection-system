package cn.ustc.courseselectionsystem.mapper;

import cn.ustc.courseselectionsystem.model.param.QueryClassMapperParam;
import cn.ustc.courseselectionsystem.model.po.*;
import cn.ustc.courseselectionsystem.sql.SqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QueryClassMapper {

    @SelectProvider(type = SqlProvider.class, method = "queryClassByParam")
    @Results(id = "classMap", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property= "code", column = "code"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "departmentId", column = "department_id"),
        @Result(property = "campus", column = "campus"),
        @Result(property = "type", column = "type"),
        @Result(property = "examMode", column = "exam_mode"),
        @Result(property = "graduateAndPostGraduate", column = "graduate_and_post_graduate"),
        @Result(property = "periodPerWeek", column = "period_per_week"),
        @Result(property = "limitCount", column = "limit_count"),
        @Result(property = "teachLang", column = "teach_lang")
    })
    List<ClassPO> queryClassByParam(QueryClassMapperParam param);

    @Select("""
           select id, code, name_cn, category_cn , classify_cn , gradation_cn , type_cn , credits, education_cn as education, period_total as periodTotal
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
        @Result(property = "education", column = "education"),
        @Result(property = "periodTotal", column = "periodTotal")
    })
    CoursePO queryCourseById(Integer id);

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
}
