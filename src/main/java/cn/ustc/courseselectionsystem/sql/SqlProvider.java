package cn.ustc.courseselectionsystem.sql;

import cn.ustc.courseselectionsystem.model.param.QueryClassMapperParam;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * SQL 语句提供类
 */
@SuppressWarnings("unused")
public class SqlProvider {
    /**
     * 提供根据参数查询课程的 SQL 语句
     * @param param 查询参数
     * @return 动态 SQL 语句
     */
    public String queryClassByParam(QueryClassMapperParam param) {
        return new SQL() {{
            SELECT("c.id, c.code, c.course_id, c.department_id, c.campus_cn, c.type_cn, c.exam_mode_cn, c.graduate_and_post_graduate, c.period_per_week, c.limit_count, c.teach_lang_cn");
            FROM("tbl_class c");
            if (StringUtils.hasText(param.getDepartmentCode())) {
                JOIN("tbl_department d on c.department_id = d.id");
                WHERE("d.code = #{departmentCode}");
            }
            if (StringUtils.hasText(param.getCourseCode())) {
                WHERE("c.code LIKE CONCAT('%', #{courseCode}, '%')");
            }
            if (StringUtils.hasText(param.getCourseName())) {
                JOIN("tbl_course co on c.course_id = co.id");
                WHERE("co.name_cn LIKE CONCAT('%', #{courseName}, '%')");
            }
            if (StringUtils.hasText(param.getTeacherName())) {
                JOIN("tbl_teacher_class tc on c.id = tc.class_id");
                JOIN("tbl_teacher t on tc.teacher_id = t.id");
                WHERE("t.name_cn LIKE CONCAT('%', #{teacherName}, '%')");
            }
        }}.toString();
    }
}
