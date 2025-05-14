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

@WebServlet(UrlPath.USER)
public class UserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private static final String USER = "user";
    private static final String ID = "id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter(ID));
        UserDto userDto = userService.findById(userId);
        req.setAttribute(USER, userDto);
        req.getRequestDispatcher(JspHelper.getPath(USER)).forward(req, resp);
    }
}
