<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<jsp:include page="WEB-INF/jsp/header.jsp"/>
<h1>Meals</h1>
<h4><a href="?action=create">Add meal</a></h4>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="meal" items="${list}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <jsp:useBean id="timeUtil" class="ru.javawebinar.topjava.util.TimeUtil"/>
        <tr class="${meal.excess ? 'red' : 'green'}">
            <td>${timeUtil.dtf.format(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="?action=update&id=${meal.id}">Update</a></td>
            <td><a href="?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
