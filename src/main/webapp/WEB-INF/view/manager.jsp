<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Управление пиццами</title>
    <style>
        .pizza-block {
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 15px;
        }
        .price-form {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<h1>Управление пиццами</h1>
<br>
<a href="/pizza">Вернуться на главную</a>
<br>
<br>
<br>
<c:forEach var="pizza" items="${pizzas}">
    <div class="pizza-block">
        <h3>${pizza.name}</h3>
        <p>Цена: ${pizza.price} руб.</p>
        <p>Статус: ${pizza.availability ? 'В наличии' : 'Нет в наличии'}</p>
        <a href="/manager/toggle/${pizza.id}">Переключить статус</a>

        <!-- Форма для обновления цены -->
        <form class="price-form" action="${pageContext.request.contextPath}/manager/changePrice/${pizza.id}" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <label for="price_${pizza.id}">Новая цена:</label>
            <input type="number" step="1" name="price" id="price_${pizza.id}" value="${pizza.price}" required>
            <button type="submit">Обновить цену</button>
        </form>
    </div>
</c:forEach>

</body>
</html>