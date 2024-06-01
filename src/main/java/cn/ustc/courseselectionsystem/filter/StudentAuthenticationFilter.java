package cn.ustc.courseselectionsystem.filter;

import cn.ustc.courseselectionsystem.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StudentAuthenticationFilter extends OncePerRequestFilter {

    @Value("${security.ignore-api-paths}")
    private List<String> ignoreApiPaths;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
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
            map = TokenUtil.parseToken(token);
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
        String path = request.getContextPath() + request.getRequestURI();
        return ignoreApiPaths.contains(path);
    }

}
