<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--<jsp:useBean id="pizza" scope="request" type="com.daniilkhanukov.spring.pizza_website.entity.Pizza"/>--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Пицца-Девица</title>
    <style>
        .pizza-container { display: flex; flex-wrap: wrap; }
        .pizza-item { width: 25%; padding: 10px; text-align: center; }
    </style>
</head>
<body>
<header>
    <a href="/pizza">Пицца</a> |
    <a href="/account">Аккаунт</a> |
    <a href="/cart">Корзина</a>
</header>
<h1>Добро пожаловать в пиццерию!</h1>
<div class="pizza-container">
    <c:forEach var="pizza" items="${pizzas}">
        <div class="pizza-item">
            <h3>${pizza.name}</h3>
            <p>Цена: ${pizza.price} руб.</p>
            <c:if test="${pizza.availability}">
                <a href="/cart/add/${pizza.id}">Добавить в корзину</a>
            </c:if>
            <c:if test="${!pizza.availability}">
                <p>Нет в наличии</p>
            </c:if>
        </div>
    </c:forEach>
</div>
</body>
</html>