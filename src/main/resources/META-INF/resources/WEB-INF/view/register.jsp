<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
        }
        .container {
            max-width: 500px;
            margin: 70px auto;
            background-color: #fff;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.15);
        }
        h1 {
            text-align: center;
            margin-bottom: 25px;
        }
        form div {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
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
        .error {
            color: #d9534f;
            font-size: 14px;
            margin-top: 5px;
        }
        .links {
            text-align: center;
            margin-top: 20px;
        }
        .links a {
            color: #333;
            text-decoration: none;
            margin: 0 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Регистрация</h1>
    <form:form method="post" action="/register" modelAttribute="userDTO">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <div>
            <label>Логин:</label>
            <form:input path="username" placeholder="Введите логин"/>
            <form:errors path="username" cssClass="error"/>
        </div>
        <div>
            <label>Почта:</label>
            <form:input path="email" placeholder="Введите вашу почту"/>
            <form:errors path="email" cssClass="error"/>
        </div>
        <div>
            <label>Пароль:</label>
            <form:password path="password" placeholder="Введите пароль"/>
            <form:errors path="password" cssClass="error"/>
        </div>
        <button type="submit">Зарегистрироваться</button>
    </form:form>
    <div class="links">
        <a href="/pizza">Вернуться на главную</a>
    </div>
</div>
</body>
</html>