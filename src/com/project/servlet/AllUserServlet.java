package com.project.servlet;

import com.project.dto.UserDto;
import com.project.service.UserService;
import com.project.utilConnection.JspHelper;
import com.project.utilConnection.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(UrlPath.USERS)
public class AllUserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private static final String USERS = "users";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> userDtos = userService.findAll();
        req.setAttribute(USERS, userDtos);
        req.getRequestDispatcher(JspHelper.getPath(USERS)).forward(req, resp);
    }
}
