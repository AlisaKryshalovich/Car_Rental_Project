package com.project.filter;

import com.project.dto.CreateUserDto;
import com.project.utilConnection.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    public static final Set<String> PUBLIC_PATH = Set.of(UrlPath.REGISTRATION, UrlPath.LOGIN);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isPublicPath(uri) || isUserLoggedIn(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");
            ((HttpServletResponse) servletResponse).sendRedirect(prevPage != null ? prevPage : UrlPath.LOGIN);
        }
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        CreateUserDto createUserDto = (CreateUserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return createUserDto != null;
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }
}
