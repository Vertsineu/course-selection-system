package cn.ustc.courseselectionsystem.service.impl;

import java.util.*;

import cn.ustc.courseselectionsystem.config.security.Student;
import cn.ustc.courseselectionsystem.mapper.StudentLoginMapper;
import cn.ustc.courseselectionsystem.model.po.StudentLoginPO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentLoginMapper studentLoginMapper;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StudentLoginPO student = studentLoginMapper.queryStudentByNumber(username);

        if (Objects.isNull(student)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return new Student(student.getNumber(), student.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
    }

}
