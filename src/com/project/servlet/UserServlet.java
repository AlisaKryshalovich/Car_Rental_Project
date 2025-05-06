package com.project.servlet;

import com.project.dto.UserDto;
import com.project.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        UserDto userDto = userService.findById(userId);
        req.setAttribute("user", userDto);
        req.getRequestDispatcher("/WEB-INF/user.jsp").forward(req, resp);
    }
}
