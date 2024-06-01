package cn.ustc.courseselectionsystem.mapper;

import org.apache.ibatis.annotations.*;

import cn.ustc.courseselectionsystem.model.po.StudentLoginPO;

@Mapper
public interface StudentLoginMapper {

    @Select("""
            select id, number, password, department_id from tbl_student
            where number = #{number}
            """)
    @Results(id = "studentPOMap", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "number", column = "number"),
        @Result(property = "password", column = "password"),
        @Result(property = "departmentId", column = "department_id")
    })
    StudentLoginPO queryStudentByNumber(String number);

    @Insert("""
            insert into tbl_student (number, password, department_id)
            values (#{number}, #{password}, #{departmentId})
            """)
    int insertStudent(StudentLoginPO student);

}
