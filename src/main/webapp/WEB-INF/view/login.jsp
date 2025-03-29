<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход</title>
</head>
<body>
<h1>Вход</h1>
<form method="post" action="/login">
    <label>Логин: <input type="text" name="username"></label><br>
    <label>Пароль: <input type="password" name="password"></label><br>
    <button type="submit">Войти</button>
</form>
<a href="/register">Регистрация</a>
</body>
</html>