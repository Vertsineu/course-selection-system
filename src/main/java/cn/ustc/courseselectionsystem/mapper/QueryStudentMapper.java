package cn.ustc.courseselectionsystem.mapper;

import cn.ustc.courseselectionsystem.model.po.StudentInfoPO;
import org.apache.ibatis.annotations.*;

/**
 * 查询学生信息相关数据库操作
 */
@Mapper
public interface QueryStudentMapper {
    /**
     * 根据学号查询学生信息
     * @param number 学号
     * @return 学生信息
     */
    @Select("""
            select s.id, s.number, s.name, d.id, d.code, d.name_cn, d.college from tbl_student s
            left join tbl_department d on s.department_id = d.id
            where number = #{number}
            """)
    @Results(id = "studentInfoPOMap", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "number", column = "number"),
        @Result(property = "name", column = "name"),
        @Result(property = "department.id", column = "id"),
        @Result(property = "department.code", column = "code"),
        @Result(property = "department.name", column = "name_cn"),
        @Result(property = "department.college", column = "college")
    })
    StudentInfoPO queryStudentInfoByNumber(String number);
}
