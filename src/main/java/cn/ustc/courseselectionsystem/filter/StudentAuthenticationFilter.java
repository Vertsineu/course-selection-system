package cn.ustc.courseselectionsystem.filter;

import cn.ustc.courseselectionsystem.exp.CookieIllegalException;
import cn.ustc.courseselectionsystem.exp.TokenIllegalException;
import cn.ustc.courseselectionsystem.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.PathContainer;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 学生认证过滤器，通过数据库查询，判断用户密码是否正确
 */
@RequiredArgsConstructor
public class StudentAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 匿名登录可以访问的路径
     */
    private final List<String> ignoreLoginPaths;

    /**
     * Token 工具类
     */
    private final TokenUtil tokenUtil;

    /**
     * 过滤器逻辑
     * @param request 请求
     * @param response 响应
     * @param filterChain 过滤器链
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (isFilterRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;
        Cookie[] cookies = request.getCookies();

        if (cookies == null)
            throw new CookieIllegalException("cookie为空");

        for (Cookie cookie : cookies)
            if ("token".equals(cookie.getName()))
                token = cookie.getValue();

        if (!StringUtils.hasText(token)) {
            throw new TokenIllegalException("token为空");
        }

        Map<String, String> map;
        try {
            map = tokenUtil.parseToken(token);
        } catch (Exception e) {
            throw new TokenIllegalException("token非法");
        }

        String username = map.get("username");
        String password = map.get("password");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password, List.of(new SimpleGrantedAuthority("ROLE_STUDENT"))));
        filterChain.doFilter(request, response);
    }

    /**
     * 判断是否可以匿名访问
     * @param request 请求
     * @return 是否需要过滤
     */
    private boolean isFilterRequest(HttpServletRequest request) {
        PathPatternParser parser = new PathPatternParser();
        String path = request.getContextPath() + request.getRequestURI();

        for (String ignoreLoginPath : ignoreLoginPaths) {
            PathPattern pathPattern = parser.parse(ignoreLoginPath);
            PathContainer pathContainer = PathContainer.parsePath(path);
            if (pathPattern.matches(pathContainer))
                return true;
        }
        return false;
    }


}
