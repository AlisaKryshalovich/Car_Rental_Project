package com.project.servlet;

import com.project.dto.CreateUserDto;
import com.project.service.UserService;
import com.project.utilConnection.JspHelper;
import com.project.utilConnection.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    private static final String LOGIN_JSP = "login";
    private static final String USERS_SERVLET = "/users";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String USER = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath(LOGIN_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter(EMAIL), req.getParameter(PASSWORD))
                .ifPresentOrElse(
                        createUserDto -> onLoginSuccess(createUserDto, req, resp),
                        () -> onLoginFail(req, resp)
                );
    }

    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect("/login?error&email=" + req.getParameter(EMAIL));
        } catch (IOException e) {
            System.err.println("Oшибка при onLoginFAIL() LoginServlet: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void onLoginSuccess(CreateUserDto createUserDto, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getSession().setAttribute(USER, createUserDto);
            resp.sendRedirect(USERS_SERVLET);
        } catch (IOException e) {
            System.err.println("Oшибка при onLoginSuccess() LoginServlet: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
