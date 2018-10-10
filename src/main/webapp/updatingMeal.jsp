<%--
  Created by IntelliJ IDEA.
  User: IndI
  Date: 08.10.2018
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Update Meal</title>
</head>
<body>
<table>
    <form action="updateMeal" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <tr>
            <input required type="datetime-local" name="dateTime" placeholder="${param.dateTime}">
        </tr>
        <tr>
            <input required type="text" name="description" placeholder="${param.description}">
        </tr>
        <tr>
            <input required type="text" name="calories" placeholder="${param.calories}">
        </tr>
        <tr>
            <<input type="submit" value="Edit">
        </tr>
    </form>
</table>
</body>
</html>
