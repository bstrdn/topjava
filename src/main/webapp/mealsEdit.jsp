<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="WEB-INF/jsp/header.jsp"/>
<h2>Edit meal</h2>
<form method="post" name="edit">
    <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.Meal" scope="request"/>
    DateTime: <input name="date" type="datetime-local" value="${meal.dateTime}" required> <br>
    Description: <input name="description" type="text" size="30" value="${meal.description}" required><br>
    Calories: <input name="calories" type="number" size="10" value="${meal.calories}" required/> <br>
    <input hidden name="id" value="${meal.id}">
    <button type="submit">Save</button>
</form>
</body>
</html>
