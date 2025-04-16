<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<%--<head>--%>
<%--    <title>Корзина</title>--%>
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
<%--        .error-message {--%>
<%--            white-space: pre-line;--%>
<%--            color: #000000;--%>
<%--            font-size: 24px;      /* увеличенный размер шрифта */--%>
<%--            font-weight: bold;    /* жирное начертание */--%>
<%--            margin: 20px 0;       /* отступы сверху и снизу */--%>
<%--            text-align: center;   /* центрирование текста */--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Ваша корзина</h1>--%>
<%--<c:if test="${not empty error}">--%>
<%--    <div class="error-message">${error}</div>--%>
<%--</c:if>--%>
<%--<c:forEach var="item" items="${cart.items}">--%>
<%--    <p>--%>
<%--            ${item.pizza.name} - ${item.pizza.price} руб. x ${item.quantity} = ${item.pizza.price * item.quantity} руб.--%>
<%--                <span class="quantity-control">--%>
<%--            <a href="/cart/decrease/${item.pizza.id}">-</a>--%>
<%--            <span>${item.quantity}</span>--%>
<%--            <a href="/cart/increase/${item.pizza.id}">+</a>--%>
<%--        </span>--%>
<%--    </p>--%>
<%--</c:forEach>--%>
<%--<h3>Итого: ${cart.totalCost} руб.</h3>--%>
<%--<form method="post" action="/cart/order">--%>
<%--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />--%>
<%--    <label for="deliveryAddress">Укажите адрес доставки:</label>--%>
<%--    <br>--%>
<%--    <input type="text" id="deliveryAddress" name="deliveryAddress"--%>
<%--           placeholder="Укажите точный адрес: 'Город, улица, дом, код от домофона'"--%>
<%--           style="width: 400px;"--%>
<%--           value="<c:out value='${user.userAddress}'/>">--%>
<%--    <br>--%>
<%--    <br>--%>
<%--    <button type="submit">Оформить заказ</button>--%>
<%--    <br>--%>
<%--    <br>--%>
<%--</form>--%>
<%--<a href="/pizza">Вернуться на главную</a>--%>
<%--</body>--%>
<%--</html>--%>
<head>
    <title>Корзина</title>
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
        .error-message {
            white-space: pre-line;
            color: #d9534f;
            font-size: 20px;
            font-weight: bold;
            text-align: center;
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 15px;
        }
        button {
            background-color: #ee6c4d;
            border: none;
            padding: 10px 15px;
            color: #fff;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
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
<div class="container">
    <h1>Ваша корзина</h1>
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>
    <c:forEach var="item" items="${cart.items}">
        <p>
                ${item.pizza.name} - ${item.pizza.price} руб. x ${item.quantity} = ${item.pizza.price * item.quantity} руб.
            <span class="quantity-control">
                <a href="/cart/decrease/${item.pizza.id}">-</a>
                <span>${item.quantity}</span>
                <a href="/cart/increase/${item.pizza.id}">+</a>
            </span>
        </p>
    </c:forEach>
    <h3>Итого: ${cart.totalCost} руб.</h3>
    <form method="post" action="/cart/order">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <div>
            <label for="deliveryAddress">Укажите адрес доставки:</label>
            <input type="text" id="deliveryAddress" name="deliveryAddress"
                   placeholder="Укажите точный адрес: 'Город, улица, дом, код от домофона'"/>
        </div>
        <button type="submit">Оформить заказ</button>
    </form>
    <div class="links">
        <a href="/pizza">Вернуться на главную</a>
    </div>
</div>
</body>
</html>