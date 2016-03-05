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
        </tr>
    </c:forEach>
</table>


</body>
</html>
