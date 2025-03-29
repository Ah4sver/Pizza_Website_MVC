<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Аккаунт</title>
</head>
<body>
<h1>Ваш аккаунт</h1>
<p>Логин: <security:authentication property="principal.username"/></p>
<p>Баллы: <security:authentication property="principal.points"/></p>
<security:authorize access="hasRole('MANAGER')">
    <p><a href="/manager">Управление пиццами</a></p>
</security:authorize>
<form method="post" action="/logout">
    <button type="submit">Выйти</button>
</form>
</body>
</html>