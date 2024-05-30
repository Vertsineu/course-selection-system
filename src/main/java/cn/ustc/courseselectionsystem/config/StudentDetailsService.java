package cn.ustc.courseselectionsystem.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cn.ustc.courseselectionsystem.mapper.StudentMapper;
import cn.ustc.courseselectionsystem.model.po.StudentPO;

public class StudentDetailsService implements UserDetailsService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StudentPO student = studentMapper.queryStudentByNumber(username);

        if (Objects.isNull(student)) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(student.getNumber(), student.getPassword(), Collections.emptyList());

    }
}
