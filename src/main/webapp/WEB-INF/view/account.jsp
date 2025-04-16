<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--<head>--%>
<%--    <title>Аккаунт</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Ваш аккаунт</h1>--%>
<%--<p>Почта: ${user.email}</p>--%>
<%--<p>Логин: ${user.username}</p>--%>
<%--<h3>Данные для доставки</h3>--%>
<%--<p>Укажите ваши данные, чтобы доставка была ещё проще:</p>--%>



<%--<form:form method="post" action="/account/update" modelAttribute="form">--%>
<%--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />--%>

<%--    <div>--%>
<%--        <label>Телефон:</label>--%>
<%--        <form:input path="phone" placeholder="Формат: +7(ххх)-ххх-хх-хх"/>--%>
<%--        <form:errors path="phone"/>--%>
<%--    </div>--%>
<%--    <br>--%>
<%--    <div>--%>
<%--        <label>Адрес:</label>--%>
<%--        <form:input path="userAddress" style="width: 400px;" placeholder="Формат: Город Москва, улица ..., дом ..., кв. ..., код домофона"/>--%>
<%--        <form:errors path="userAddress"/>--%>
<%--    </div>--%>
<%--    <br>--%>
<%--    <div>--%>
<%--        <button type="submit">Сохранить данные доставки</button>--%>
<%--    </div>--%>

<%--</form:form>--%>

<%--<form method="post" action="/logout">--%>
<%--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />--%>
<%--    <button type="submit">Выйти</button>--%>
<%--</form>--%>
<%--<p><a href="/pizza">Вернуться на главную</a></p>--%>
<%--</body>--%>
<%--</html>--%>
<head>
    <title>Аккаунт</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #fff;
            padding: 10px 20px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        header a {
            text-decoration: none;
            color: #333;
            margin-right: 15px;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1, h3 {
            text-align: center;
        }
        p {
            font-size: 16px;
            margin: 10px 0;
        }
        form div {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            background-color: #ee6c4d;
            border: none;
            padding: 10px 15px;
            color: #fff;
            border-radius: 4px;
            cursor: pointer;
            display: block;
            margin: 0 auto;
        }
        button:hover {
            background-color: #d95c3f;
        }
        .links {
            text-align: center;
            margin-top: 20px;
        }
        .links a {
            text-decoration: none;
            color: #337;
        }
    </style>
</head>
<body>
<header>
    <a href="/pizza">Пицца</a> |
    <a href="/account">Аккаунт</a> |
    <security:authorize access="isAuthenticated()">
        <a href="/cart">Корзина</a>
    </security:authorize>
    <form method="post" action="/logout" style="display:inline;">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button type="submit">Выйти</button>
    </form>
</header>
<div class="container">
    <h1>Ваш аккаунт</h1>
    <p><strong>Почта:</strong> ${user.email}</p>
    <p><strong>Логин:</strong> ${user.username}</p>
    <h3>Данные для доставки</h3>
    <p>Укажите ваши данные, чтобы доставка была ещё проще:</p>
    <form:form method="post" action="/account/update" modelAttribute="form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <div>
            <label>Телефон:</label>
            <form:input path="phone" placeholder="Формат: +7(ххх)-ххх-хх-хх"/>
            <form:errors path="phone" cssClass="error"/>
        </div>
        <div>
            <label>Адрес:</label>
            <form:input path="userAddress" style="width:100%;" placeholder="Формат: Город Москва, улица ..., дом ..., кв. ..., код домофона"/>
            <form:errors path="userAddress" cssClass="error"/>
        </div>
        <button type="submit">Сохранить данные доставки</button>
    </form:form>
    <div class="links">
        <a href="/pizza">Вернуться на главную</a>
    </div>
<%--    <p style="text-align:center;"><a href="/pizza">Вернуться на главную</a></p>--%>
</div>
</body>
</html>