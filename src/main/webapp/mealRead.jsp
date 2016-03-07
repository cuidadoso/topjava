<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal read</title>
</head>
<body>
<h2>Meal read</h2>

<form action="update" method="get">
    <table>
        <tr>
            <td align="left"><input type="text" name="dateTime" value="${meal.dateTime}"/></td>
            <td align="left"><input type="text" name="description" value="${meal.description}"/></td>
            <td align="left"><input type="text" name="calories" value="${meal.calories}"/></td>
        </tr>
    </table>
    <input type="hidden" name="id" value="${meal.id}"/>
    <input type="submit" value="Update Id : ${meal.id}">
</form>

</body>
</html>
