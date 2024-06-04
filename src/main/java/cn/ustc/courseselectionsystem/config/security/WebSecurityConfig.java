package cn.ustc.courseselectionsystem.config.security;

import cn.ustc.courseselectionsystem.filter.StudentAuthenticationFilter;
import cn.ustc.courseselectionsystem.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    /**
     * 匿名登录可以访问的路径
     */
    @Value("${security.ignore-login-paths}")
    private String[] ignoreLoginPaths;

    /**
     * 静态资源路径
     */
    @Value("${security.ignore-static-paths}")
    private String[] ignoreStaticPaths;

    /**
     * Token 工具类
     */
    private final TokenUtil tokenUtil;

    /**
     * 配置 Spring Security 过滤器链
     * @param http HttpSecurity 配置对象
     * @return SecurityFilterChain 过滤器链
     * @throws Exception 可能出现的所有异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(ignoreLoginPaths).permitAll()
                    .anyRequest().authenticated())
                .addFilterBefore(new StudentAuthenticationFilter(List.of(ignoreLoginPaths), tokenUtil), UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    /**
     * 配置 WebSecurityCustomizer，忽略静态资源路径经过 Spring Security
     * @return WebSecurityCustomizer 对象
     */
    @Bean
    public WebSecurityCustomizer securityWebFilterChain() {
        return web -> web.ignoring()
                .requestMatchers(ignoreStaticPaths);
    }

    /**
     * 获取 AuthenticationManager 对象
     * @param authenticationConfiguration AuthenticationConfiguration 对象
     * @return AuthenticationManager 对象
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 配置密码加密器
     * @return PasswordEncoder 对象
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
