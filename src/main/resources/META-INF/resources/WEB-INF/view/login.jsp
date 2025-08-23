<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Вход</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
        }
        .container {
            max-width: 400px;
            margin: 70px auto;
            background-color: #fff;
            padding: 25px;
            border-radius: 5px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.15);
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 8px;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            width: 100%;
            background-color: #ee6c4d;
            color: #fff;
            border: none;
            padding: 10px;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #d95c3f;
        }
        .links {
            text-align: center;
            margin-top: 20px;
        }
        .links a {
            color: #337;
            text-decoration: none;
            margin: 0 30px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Вход</h1>
    <c:if test="${not empty sessionScope.loginError}">
        <div class="alert alert-danger">${sessionScope.loginError}</div>
        <c:remove var="loginError" scope="session" />
    </c:if>
    <c:if test="${param.logout != null}">
        <div class="alert alert-success">Вы успешно вышли</div>
    </c:if>
    <form method="post" action="/login">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <label>Почта:</label>
        <input type="text" name="email" placeholder="Введите вашу почту" value="${sessionScope.lastEmail}"/>
        <c:remove var="lastEmail" scope="session" />
        <label>Пароль:</label>
        <input type="password" name="password" placeholder="Введите ваш пароль"/>
        <button type="submit">Войти</button>
    </form>
    <div class="links">
        <a href="/register">Регистрация</a>
        <a href="/pizza">Вернуться на главную</a>
    </div>
</div>
</body>
</html>