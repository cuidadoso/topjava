<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2>Meal list</h2>

<table>
    <c:forEach var="meal" items="${list}">
        <c:set var="color" value="green"/>
        <c:if test="${meal.exceed}">
            <c:set var="color" value="red"/>
        </c:if>
        <tr style="color: ${color}">
            <td align="left">${meal.dateTime}</td>
            <td align="left">${meal.description}</td>
            <td align="left">${meal.calories}</td>
            <td>
                <form action="read" method="get">
                    <p><input type="hidden" name="id" value="${meal.id}"/><input type="submit" value="Read Id : ${meal.id}"></p>
                </form>
            </td>
            <td>
                <form action="delete" method="get">
                    <p><input type="hidden" name="id" value="${meal.id}"/><input type="submit" value="Delete Id : ${meal.id}"></p>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<form action="create" method="get">
    <table>
        <tr>
            <td align="left"><label>Дата/время</label><input type="text" name="dateTime" value="${meal.dateTime}"/></td>
            <td align="left"><label>Описание</label><input type="text" name="description" value="${meal.description}"/></td>
            <td align="left"><label>Калории</label><input type="text" name="calories" value="${meal.calories}"/></td>
        </tr>
    </table>
    <input type="submit" value="Create">
</form>

</body>
</html>
