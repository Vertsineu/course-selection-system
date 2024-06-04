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

/**
 * 用户认证服务实现类
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 学生登录数据库相关操作类
     */
    private final StudentLoginMapper studentLoginMapper;

    /**
     * 根据用户名加载用户数据
     * @param username 标识需要其数据的用户的用户名
     * @return 所请求的 UserDetails
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StudentLoginPO student = studentLoginMapper.queryStudentLoginByNumber(username);

        if (Objects.isNull(student)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return new Student(student.getNumber(), student.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
    }

}
