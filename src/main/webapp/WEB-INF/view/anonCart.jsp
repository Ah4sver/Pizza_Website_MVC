<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<%--<head>--%>
<%--    <title>Корзина (анонимный пользователь)</title>--%>
<%--    <style>--%>
<%--        .quantity-control {--%>
<%--            display: inline-block;--%>
<%--            margin-left: 10px;--%>
<%--        }--%>
<%--        .quantity-control a {--%>
<%--            text-decoration: none;--%>
<%--            padding: 5px 10px;--%>
<%--            border: 1px solid #ccc;--%>
<%--            background-color: #f0f0f0;--%>
<%--            color: #333;--%>
<%--        }--%>
<%--        .quantity-control a:hover {--%>
<%--            background-color: #e0e0e0;--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Ваша корзина</h1>--%>
<%--<c:forEach var="item" items="${sessionCart.items}">--%>
<%--    <p>--%>
<%--            ${item.pizza.name} - ${item.pizza.price} руб. x ${item.quantity} = ${item.pizza.price * item.quantity} руб.--%>
<%--        <span class="quantity-control">--%>
<%--            <a href="/cart/anonymous/decrease/${item.pizza.id}">-</a>--%>
<%--            <span>${item.quantity}</span>--%>
<%--            <a href="/cart/anonymous/increase/${item.pizza.id}">+</a>--%>
<%--        </span>--%>
<%--    </p>--%>
<%--</c:forEach>--%>
<%--<h3>Итого: ${sessionCart.totalCost} руб.</h3>--%>
<%--<p>Для оформления заказа необходимо <a href="/login">войти</a> или <a href="/register">зарегистрироваться</a>.</p>--%>
<%--<a href="/pizza">Вернуться</a>--%>
<%--</body>--%>
<%--</html>--%>
<head>
    <title>Корзина (анонимный пользователь)</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
        }
        .container {
            max-width: 600px;
            margin: 70px auto;
            background-color: #fff;
            padding: 25px;
            border-radius: 5px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.15);
        }
        h1 {
            text-align: center;
            margin-bottom: 25px;
        }
        p {
            font-size: 16px;
            margin-bottom: 10px;
        }
        .quantity-control a {
            text-decoration: none;
            padding: 5px 8px;
            border: 1px solid #ccc;
            background-color: #ee6c4d;
            color: #fff;
            border-radius: 3px;
            margin: 0 5px;
        }
        .quantity-control a:hover {
            background-color: #d95c3f;
        }
        .links {
            text-align: center;
            margin-top: 20px;
            border: 1px solid #ccc;
            background-color: #ee6c4d;
            color: #fff;
        }
        .links a {
            text-decoration: none;
            color: #333;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Ваша корзина</h1>
    <c:forEach var="item" items="${sessionCart.items}">
        <p>
                ${item.pizza.name} - ${item.pizza.price} руб. x ${item.quantity} = ${item.pizza.price * item.quantity} руб.
            <span class="quantity-control">
                <a href="/cart/anonymous/decrease/${item.pizza.id}">-</a>
                <span>${item.quantity}</span>
                <a href="/cart/anonymous/increase/${item.pizza.id}">+</a>
            </span>
        </p>
    </c:forEach>
    <h3>Итого: ${sessionCart.totalCost} руб.</h3>
    <p style="text-align:center;">Для оформления заказа необходимо <a href="/login">войти</a> или <a href="/register">зарегистрироваться</a>.</p>
    <div class="links">
        <a href="/pizza">Вернуться</a>
    </div>
</div>
</body>
</html>
