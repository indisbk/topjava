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
            <td>Date/Time</td>
            <td>Description</td>
            <td>Calories</td>
            <td>Exceed</td>
        </tr>
        <%--@elvariable id="mealWithExceeds" type="java.util.List"--%>
        <c:forEach items="${mealWithExceeds}" var="meal">
            <tr style="${meal.isExceed() ? 'color : red' : 'color : green'}">
                <td>
                    <javatime:format value="${meal.getDateTime()}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                <td>${meal.getDescription()}</td>
                <td>${meal.getCalories()}</td>
                <td>${meal.isExceed() ? "Exceeded" : "Not Exceeded"}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
