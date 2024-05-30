package cn.ustc.courseselectionsystem.service.impl;

import java.util.*;

import cn.ustc.courseselectionsystem.config.security.Student;
import cn.ustc.courseselectionsystem.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentMapper studentMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        StudentPO student = studentMapper.queryStudentByNumber(username);
//
//        if (Objects.isNull(student)) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        return new Student(student.getNumber(), student.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
        return new Student("username", passwordEncoder.encode("password"), List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
    }

}
