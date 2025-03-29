<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<jsp:useBean id="pizzas" scope="request" type="com.daniilkhanukov.spring.pizza_website.entity.Pizza"/>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Управление пиццами</title>
</head>
<body>
<h1>Управление пиццами</h1>
<c:forEach var="pizza" items="${pizzas}">
    <div>
        <h3>${pizza.name}</h3>
        <p>Цена: ${pizza.price} руб.</p>
        <p>Статус: ${pizza.active ? 'В наличии' : 'Нет в наличии'}</p>
        <a href="/manager/toggle/${pizza.id}">Переключить статус</a>
    </div>
</c:forEach>
<a href="/pizza">Вернуться</a>
</body>
</html>