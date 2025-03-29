<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<h1>Регистрация</h1>
<c:if test="${not empty error}">
    <div style="color: red;">${error}</div>
</c:if>
<form method="post" action="/register">
    <label>Логин: <input type="text" name="username"></label><br>
    <label>Почта: <input type="email" name="email"></label><br>
    <label>Пароль: <input type="password" name="password"></label><br>
    <button type="submit">Зарегистрироваться</button>
</form>
</body>
</html>