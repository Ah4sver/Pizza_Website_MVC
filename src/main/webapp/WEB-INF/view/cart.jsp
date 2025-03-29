<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:useBean id="cart" scope="request" type="com.daniilkhanukov.spring.pizza_website.entity.Cart"/>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Корзина</title>
</head>
<body>
<h1>Ваша корзина</h1>
<c:forEach var="item" items="${cart.items}">
    <p>${item.pizza.name} - ${item.pizza.price} руб. x ${item.quantity} = ${item.pizza.price * item.quantity} руб.</p>
</c:forEach>
<h3>Итого: ${cart.totalCost} руб.</h3>
<form method="post" action="/cart/order">
    <label>Город: <input type="text" name="city"></label><br>
    <label>Улица: <input type="text" name="street"></label><br>
    <label>Код домофона: <input type="text" name="intercomCode"></label><br>
    <button type="submit">Оформить заказ</button>
</form>
<a href="/pizza">Вернуться</a>
</body>
</html>