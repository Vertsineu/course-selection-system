package cn.ustc.courseselectionsystem.mapper;

import cn.ustc.courseselectionsystem.model.po.StudentRegisterPO;
import org.apache.ibatis.annotations.*;

import cn.ustc.courseselectionsystem.model.po.StudentLoginPO;

/**
 * 学生登陆相关数据库操作
 */
@Mapper
public interface StudentLoginMapper {

    /**
     * 根据学号查询学生登录信息
     * @param number 学号
     * @return 学生登录信息
     */
    @Select("""
            select id, number, password from tbl_student
            where number = #{number}
            """)
    @Results(id = "studentPOMap", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "number", column = "number"),
        @Result(property = "password", column = "password")
    })
    StudentLoginPO queryStudentLoginByNumber(String number);

    /**
     * 插入学生注册信息
     * @param student 学生注册信息
     * @return 插入条数
     */
    @Insert("""
            insert into tbl_student (number, name, password, department_id)
            values (#{number}, #{name}, #{password}, #{departmentId})
            """)
    int insertStudent(StudentRegisterPO student);

}
