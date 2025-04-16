<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--<head>--%>
<%--    <title>Пицца-Девица</title>--%>
<%--    <style>--%>
<%--        body {--%>
<%--            font-family: Arial, sans-serif;--%>
<%--            background-color: #f4f4f4;--%>
<%--            color: #333;--%>
<%--        }--%>
<%--        .pizza-container {--%>
<%--            display: flex;--%>
<%--            flex-wrap: wrap;--%>
<%--            justify-content: center;--%>
<%--        }--%>
<%--        .pizza-item {--%>
<%--            width: 200px;--%>
<%--            background-color: #fff;--%>
<%--            border: 1px solid #ddd;--%>
<%--            border-radius: 5px;--%>
<%--            padding: 15px;--%>
<%--            margin: 10px;--%>
<%--            box-shadow: 0 2px 5px rgba(0,0,0,0.1);--%>
<%--            text-align: center;--%>
<%--        }--%>
<%--        .pizza-item img {--%>
<%--            max-width: 100%;--%>
<%--            height: auto;--%>
<%--            border-radius: 5px;--%>
<%--        }--%>

<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<header>--%>
<%--    <a href="/pizza">Пицца</a> |--%>
<%--    <a href="/account">Аккаунт</a> |--%>
<%--    <security:authorize access="isAuthenticated()">--%>
<%--        <a href="/cart">Корзина</a>--%>
<%--    </security:authorize>--%>
<%--    <security:authorize access="!isAuthenticated()">--%>
<%--        <a href="/cart/anonymous">Корзина</a>--%>
<%--    </security:authorize>--%>
<%--    <security:authorize access="hasRole('MANAGER')">--%>
<%--        <p><a href="/manager">Управление пиццами</a></p>--%>
<%--    </security:authorize>--%>
<%--    <security:authorize access="hasRole('MANAGER')">--%>
<%--        <p><a href="/manager/orders">Управление заказами</a></p>--%>
<%--    </security:authorize>--%>

<%--</header>--%>
<%--<h1>Добро пожаловать в пиццерию!</h1>--%>
<%--<div class="pizza-container">--%>
<%--    <c:forEach var="pizza" items="${pizzas}">--%>

<%--        <div class="pizza-item">--%>
<%--            <img class="pizza-image" src="<c:url value='/images/pizza${pizza.id}.jpg'/>" alt="${pizza.name}" />--%>
<%--            <h3>${pizza.name}</h3>--%>
<%--            <p> ${pizza.description} </p>--%>
<%--            <p>Цена: ${pizza.price} руб.</p>--%>
<%--            <c:if test="${pizza.availability}">--%>
<%--                <a href="/cart/add/${pizza.id}">Добавить в корзину</a>--%>
<%--            </c:if>--%>
<%--            <c:if test="${!pizza.availability}">--%>
<%--                <p>Нет в наличии</p>--%>
<%--            </c:if>--%>
<%--        </div>--%>
<%--    </c:forEach>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
<head>
    <title>Пицца-Девица</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 0; /* Убираем отступы */
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

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        .pizza-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center; /* центрируем карточки */
            padding: 0 20px;
        }

        .pizza-item {
            width: 220px;                  /* Чуть шире для картинки */
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: center;
            transition: transform 0.3s ease, box-shadow 0.3s ease, background-color 0.3s ease;
            padding: 15px; /* немного отступов */
        }

        /* Добавляем эффект при наведении */
        .pizza-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
            background-color: #f8f8f8; /* немного светлее/темнее при наведении */
        }

        .pizza-item img {
            max-width: 100%;
            height: auto;
            border-radius: 5px;
            margin-bottom: 10px;
        }

        .pizza-item h3 {
            font-size: 18px;
            margin: 10px 0 5px;
        }

        .pizza-item p {
            margin: 5px 0;
        }

        .pizza-item a {
            display: inline-block;
            text-decoration: none;
            color: #fff;
            background-color: #ee6c4d; /* приятный рыжевато-оранжевый цвет */
            padding: 8px 12px;
            border-radius: 4px;
            transition: background-color 0.2s ease;
            font-weight: bold;
        }

        .pizza-item a:hover {
            background-color: #d95c3f;
        }

        .pizza-item .unavailable {
            color: #999;
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
    <security:authorize access="!isAuthenticated()">
        <a href="/cart/anonymous">Корзина</a>
    </security:authorize>
    <security:authorize access="hasRole('MANAGER')">
        ㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤ|<a href="/manager">Управление пиццами</a> |
        <a href="/manager/orders">Управление заказами</a>
    </security:authorize>
</header>

<h1>Добро пожаловать в пиццерию!</h1>

<div class="pizza-container">
    <c:forEach var="pizza" items="${pizzas}">
        <div class="pizza-item">
            <img src="<c:url value='/images/pizza${pizza.id}.jpg'/>" alt="${pizza.name}" />
            <h3>${pizza.name}</h3>
            <p>${pizza.description}</p>
            <p>Цена: ${pizza.price} руб.</p>

            <c:if test="${pizza.availability}">
                <a href="/cart/add/${pizza.id}">Добавить в корзину</a>
            </c:if>
            <c:if test="${!pizza.availability}">
                <p class="unavailable">Нет в наличии</p>
            </c:if>
        </div>
    </c:forEach>
</div>

</body>
</html>