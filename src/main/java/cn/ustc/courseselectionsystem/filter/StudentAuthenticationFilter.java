package cn.ustc.courseselectionsystem.filter;

import cn.ustc.courseselectionsystem.util.TokenUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class StudentAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isFilterRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            throw new RuntimeException("token为空");
        }

        Map<String, String> map;
        try {
            map = tokenUtil.parseToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        String username = map.get("username");
        String password = map.get("password");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password, List.of(new SimpleGrantedAuthority("ROLE_STUDENT"))));
        filterChain.doFilter(request, response);
    }

    private boolean isFilterRequest(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String filterPath = request.getRequestURI();
        String path = contextPath + filterPath;
        return "/api/account/login".equals(path);
    }

}
