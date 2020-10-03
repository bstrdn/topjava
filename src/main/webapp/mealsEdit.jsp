<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="WEB-INF/jsp/header.jsp"/>
<h2>Edit meal</h2>
<form method="post" name="edit">
    <%--                <jsp:useBean id="meal" scope="request" class="ru.javawebinar.topjava.model.MealTo"/>--%>
    DateTime: <input name="date" type="datetime-local" value="${meal.dateTime}" required> <br>
    Description: <input name="des" type="text" size="30" value="${meal.description}" required><br>
    Calories: <input name="cal" type="number" size="10" value="${meal.calories}" required/> <br>
    <input hidden name="id" value="${meal.id}">
    <button type="submit">Save</button>
</form>
</body>
</html>
