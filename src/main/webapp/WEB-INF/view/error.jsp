<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ошибка</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        h1 {
            color: #ff4444;
        }
        p {
            font-size: 18px;
        }
        a {
            color: #007bff;
            text-decoration: none;
        }
    </style>
</head>
<body>
<h1>Упс, произошла ошибка</h1>
<p>Код ошибки: ${statusCode}</p>
<p>${errorMessage}</p>
<p><a href="/pizza">Вернуться на главную</a></p>
</body>
</html>