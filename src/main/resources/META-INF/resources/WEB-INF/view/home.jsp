<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
            justify-content: center;
            padding: 0 20px;
        }

        .pizza-item {
            width: 220px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: center;
            transition: transform 0.3s ease, box-shadow 0.3s ease, background-color 0.3s ease;
            padding: 15px; /* немного отступов */
        }


        .pizza-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
            background-color: #f8f8f8;
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
            background-color: #ee6c4d;
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
<%--                <a href="/cart/add/${pizza.id}">Добавить в корзину</a>--%>
                <a href="/cart/add/${pizza.id}" class="add-to-cart-btn">Добавить в корзину</a>
            </c:if>
            <c:if test="${!pizza.availability}">
                <p class="unavailable">Нет в наличии</p>
            </c:if>
        </div>
    </c:forEach>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const addToCartButtons = document.querySelectorAll('.add-to-cart-btn');

        addToCartButtons.forEach(button => {
            button.addEventListener('click', function (event) {
                // 1. Отменяем стандартное поведение ссылки (переход по URL)
                event.preventDefault();

                const url = this.href;

                // 2. Отправляем запрос на сервер в фоновом режиме
                fetch(url, {
                    method: 'GET', // Или 'POST', если у вас CSRF требует этого
                    headers: {
                        // Если потребуется, здесь можно добавить заголовки, например, для CSRF
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            // 3. Сообщаем пользователю об успехе (можно сделать красивее)
                            // Например, можно изменить текст кнопки на "Добавлено!" на 2 секунды
                            const originalText = this.textContent;
                            this.textContent = 'Добавлено!';
                            setTimeout(() => {
                                this.textContent = originalText;
                            }, 500);
                        } else {
                            alert('Ошибка при добавлении в корзину.');
                        }
                    })
                    .catch(error => {
                        console.error('Fetch error:', error);
                        alert('Сетевая ошибка.');
                    });
            });
        });
    });
</script>

</body>
</html>