<%@ page import="java.util.List" %>
<%@ page import="com.project.dto.UserDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<head>
    <title>Список пользователей</title>
</head>
<body>
<h1>Список пользователей</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Роль</th>
    </tr>
    <% for (UserDto userDto : (List<UserDto>) request.getAttribute("users")) { %>
    <tr>
        <td><%= userDto.getUserId() %></td>
        <td><%= userDto.getFirstName() %></td>
        <td><%= userDto.getLastName() %></td>
        <td><%= userDto.getRole() %></td>
    </tr>
    <% } %>
</table>
</body>
</html>