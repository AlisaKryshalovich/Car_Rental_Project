<%@ page import="com.project.dto.UserDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Пользователь</title>
</head>
<body>
<h1>Информация о пользователе</h1>
<%
  UserDto user = (UserDto) request.getAttribute("user");
%>
    <p>ID: <%= user.getUserId() %></p>
    <p>Имя: <%= user.getFirstName() %></p>
    <p>Фамилия: <%= user.getLastName() %></p>
    <p>Роль: <%= user.getRole() %></p>
</body>
</html>
