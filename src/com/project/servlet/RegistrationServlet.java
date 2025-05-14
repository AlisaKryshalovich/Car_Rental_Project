package com.project.servlet;

import com.project.dto.CreateUserDto;
import com.project.entity.Role;
import com.project.exception.ValidationException;
import com.project.service.UserService;
import com.project.utilConnection.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private static final String REGISTRATION_JSP = "registration";
    private static final String LOGIN_SERVLET = "/login";
    private static final String ROLES = "roles";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String AGE = "age";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String ERRORS = "errors";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ROLES, Role.values());

        req.getRequestDispatcher(JspHelper.getPath(REGISTRATION_JSP))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CreateUserDto createUserDto = CreateUserDto.builder()
                .firstName(req.getParameter(FIRST_NAME))
                .lastName(req.getParameter(LAST_NAME))
                .age(req.getParameter(AGE))
                .email(req.getParameter(EMAIL))
                .password(req.getParameter(PASSWORD))
                .role(req.getParameter(ROLE))
                .build();
        try {
            userService.create(createUserDto);
            resp.sendRedirect(LOGIN_SERVLET);
//            resp.sendRedirect(JspHelper.getPath(LOGIN_JSP));
        } catch (ValidationException exception) {
            req.setAttribute(ERRORS, exception.getErrors());
            doGet(req, resp);
        }
    }
}
