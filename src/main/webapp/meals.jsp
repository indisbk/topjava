<%--
  Created by IntelliJ IDEA.
  User: IndI
  Date: 05.10.2018
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
    <table border="1">
        <tr>
            <td>ID</td>
            <td>Date/Time</td>
            <td>Description</td>
            <td>Calories</td>
            <td>Exceed</td>
            <td>Actions</td>
        </tr>
        <%--@elvariable id="mealWithExceeds" type="java.util.List"--%>
        <c:forEach items="${mealWithExceeds}" var="meal">
            <tr style="${meal.isExceed() ? 'color : red' : 'color : green'}">
                <td>${meal.getId()}</td>
                <td>
                    <javatime:format value="${meal.getDateTime()}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                <td>${meal.getDescription()}</td>
                <td>${meal.getCalories()}</td>
                <td>${meal.isExceed() ? "Exceeded" : "Not Exceeded"}</td>
                <td>
                    <form action="updatingMeal.jsp" method="post">
                        <input type="hidden" name="id" value="${meal.getId().get()}">
                        <input type="hidden" name="dateTime" value="${meal.getDateTime()}">
                        <input type="hidden" name="description" value="${meal.getDescription()}">
                        <input type="hidden" name="calories" value="${meal.getCalories()}">
                        <input type="submit" value="Edit" style="float:left">
                    </form>
                </td>
                <td>
                    <form action="deleteMeal" method="post">
                        <input type="hidden" name="id" value="${meal.getId().get()}">
                        <input type="submit" value="Delete" style="float:left">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
<p>
</p>
<table>
<form action="addMeal" method="post">
    <tr>
        Date/Time
    <input required type="datetime-local" name="dateTime">
    </tr>
    <tr>
        Description
    <input required type="text" name="description">
    </tr>
    <tr>
        Calories
    <input required type="text" name="calories">
    </tr>
    <tr>
    <input type="submit" value="Add meal">
    </tr>
</form>
</table>
</body>
</html>
