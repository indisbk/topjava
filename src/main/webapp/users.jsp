<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2></h2>
<table border="2" cellpadding="1" cellspacing="0">
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Calories</th>
        <th>Meal</th>
    </tr>
    <tr>
        <jsp:useBean id="user" scope="request" type="ru.javawebinar.topjava.model.User"/>
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td>${user.caloriesPerDay}</td>
        <td>
            <a href="meals">Meals</a>
        </td>
    </tr>
</table>
</body>
</html>