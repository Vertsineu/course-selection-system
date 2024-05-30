package cn.ustc.courseselectionsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import cn.ustc.courseselectionsystem.model.po.StudentPO;

@Mapper
public interface StudentMapper {

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
    public StudentPO queryStudentByNumber(String number);

}
