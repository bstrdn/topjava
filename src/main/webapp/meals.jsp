<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>

<h1>Meals</h1>
<h4><a href="">Add meal</a></h4>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="meal" items="${list}">
        <%--    <jsp:useBean id="meal" scope="request" class="ru.javawebinar.topjava.model.MealTo"/>--%>
        <jsp:useBean id="timeUtil" scope="request" class="ru.javawebinar.topjava.util.TimeUtil"/>
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <td>${timeUtil.getDateTimeFormatter(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>Update</td>
            <td>${meal.excess}Delete</td>
        </tr>

    </c:forEach>
</table>
</body>
</html>
