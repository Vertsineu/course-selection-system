package cn.ustc.courseselectionsystem.mapper;

import cn.ustc.courseselectionsystem.model.po.ClassPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseSelectMapper {

    @Select("""
            select c.id, c.course_id, c.department_id, c.code, c.campus_cn, c.type_cn, c.exam_mode_cn, c.graduate_and_post_graduate, c.period_per_week, c.limit_count, c.teach_lang_cn
            from tbl_student_class sc
            join tbl_class c on sc.class_id = c.id
            where student_id = #{studentId}
            """)
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
    List<ClassPO> queryCheckedClass(Integer studentId);

    @Insert("""
          insert into tbl_student_class (class_id, student_id) values (#{classId}, #{studentId})
          """)
    int insertClass(Integer classId, Integer studentId);

    @Delete("""
          delete from tbl_student_class where class_id = #{classId} and student_id = #{studentId}
          """)
    int deleteClass(Integer classId, Integer studentId);


    // 返回select语句返回的entry数
    @Select("""
            select count(*)
            from tbl_student_class
            where class_id = #{classId}
            """)
    int querySelectedCountById(Integer classId);

    @Select("""
            select limit_count
            from tbl_class
            where id = #{classId}
            """)
    int queryLimitCountById(Integer classId);

    @Select("""
            select count(*)
            from tbl_student_class
            where student_id = #{studentId} and class_id = #{classId}
            """)
    int querySelectedById(Integer classId, Integer studentId);
}
