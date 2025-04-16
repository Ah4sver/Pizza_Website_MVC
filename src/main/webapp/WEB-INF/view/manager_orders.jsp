<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список заказов</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #333;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .delete-button {
            color: #ffffff;
            background-color: #d9534f;
            padding: 5px 10px;
            text-decoration: none;
            border-radius: 4px;
        }
        .delete-button:hover {
            background-color: #c9302c;
        }
        .modal-overlay {
            display: none; /* скрыто по умолчанию */
            position: fixed;
            z-index: 9999;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.5); /* полупрозрачный фон */
        }
        .modal-content {
            background-color: #ffffff;
            margin: 15% auto; /* 15% от верхней части и по центру */
            padding: 20px;
            border: 1px solid #888;
            width: 300px; /* можно изменить под свои нужды */
            text-align: center;
            border-radius: 8px;
        }
        .modal-content p {
            font-size: 18px;
        }
        .modal-buttons {
            margin-top: 20px;
        }
        .modal-buttons button {
            margin: 0 10px;
            padding: 8px 16px;
            font-size: 16px;
            cursor: pointer;
        }
        .modal-yes {
            background-color: #5cb85c;
            color: white;
            border: none;
            border-radius: 4px;
        }
        .modal-no {
            background-color: #d9534f;
            color: white;
            border: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<h1>Заказы за последнее время</h1>
<c:if test="${empty orders}">
    <p>Заказов не найдено.</p>
</c:if>
<table>
    <thead>
    <tr>
        <th>Номер заказа</th>
        <th>Пользователь (юзернейм)</th>
        <th>Почта</th>
        <th>Пиццы (наименование и количество)</th>
        <th>Адрес доставки</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.id}</td>
            <td>${order.user.username}</td>
            <td>${order.user.email}</td>
            <td>
                <c:forEach var="item" items="${order.cart.items}">
                    ${item.pizza.name} (x${item.quantity})<br/>
                </c:forEach>
            </td>
            <td>${order.deliveryAddress}</td>
            <td>
                <!-- Здесь вызывается функция confirmDelete с параметром order.id -->
                <a class="delete-button" href="javascript:void(0);" onclick="confirmDelete(${order.id});">
                    Удалить заказ
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="/pizza">Вернуться на главную</a></p>

<!-- Модальное окно подтверждения -->
<div id="confirmModal" class="modal-overlay">
    <div class="modal-content">
        <p>Вы точно уверены в своём выборе?</p>
        <div class="modal-buttons">
            <button class="modal-yes" id="confirmYes">Да</button>
            <button class="modal-no" id="confirmNo">Нет</button>
        </div>
    </div>
</div>

<script type="text/javascript">
    function confirmDelete(orderId) {
        var modal = document.getElementById("confirmModal");
        modal.style.display = "block";

        // При нажатии "Да" переход к URL для удаления
        document.getElementById("confirmYes").onclick = function() {
            modal.style.display = "none";
            window.location.href = "/manager/orders/delete/" + orderId;
        };

        // При нажатии "Нет" закрываем модальное окно
        document.getElementById("confirmNo").onclick = function() {
            modal.style.display = "none";
        };

        // Закрытие окна при клике вне его содержимого (необязательно)
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        };
    }
</script>
</body>
</html>