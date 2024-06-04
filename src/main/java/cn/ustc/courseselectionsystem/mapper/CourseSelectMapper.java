package cn.ustc.courseselectionsystem.mapper;

import cn.ustc.courseselectionsystem.model.po.ClassPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 选课相关的数据库操作
 */
@Mapper
public interface CourseSelectMapper {

    /**
     * 查询学生已选课程
     * @param studentId 学生id
     * @return 已选课程列表
     */
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

    /**
     * 添加选课
     * @param classId 课程id
     * @param studentId 学生id
     * @return 是否添加成功
     */
    @Insert("""
          insert into tbl_student_class (class_id, student_id) values (#{classId}, #{studentId})
          """)
    int insertClass(Integer classId, Integer studentId);

    /**
     * 删除选课
     * @param classId 课程id
     * @param studentId 学生id
     * @return 是否删除成功
     */
    @Delete("""
          delete from tbl_student_class where class_id = #{classId} and student_id = #{studentId}
          """)
    int deleteClass(Integer classId, Integer studentId);

    /**
     * 查询课堂已选人数
     * @param classId 课程id
     * @return 已选人数
     */
    @Select("""
            select count(*)
            from tbl_student_class
            where class_id = #{classId}
            """)
    int querySelectedCountById(Integer classId);

    /**
     * 查询课堂限选人数
     * @param classId 课程id
     * @return 限选人数
     */
    @Select("""
            select limit_count
            from tbl_class
            where id = #{classId}
            """)
    int queryLimitCountById(Integer classId);

    /**
     * 查询学生是否已选课
     * @param classId 课程id
     * @param studentId 学生id
     * @return 是否已选
     */
    @Select("""
            select count(*)
            from tbl_student_class
            where student_id = #{studentId} and class_id = #{classId}
            """)
    int querySelectedById(Integer classId, Integer studentId);
}
