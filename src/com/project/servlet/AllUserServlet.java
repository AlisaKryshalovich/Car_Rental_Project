package com.project.servlet;

import com.project.dto.UserDto;
import com.project.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class AllUserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> userDtos = userService.findAll();
        req.setAttribute("users", userDtos);
        req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
    }
}
