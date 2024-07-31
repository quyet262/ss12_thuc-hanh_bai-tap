<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 7/31/2024
  Time: 9:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Country co</h1>
<table>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>email</th>
        <th>country</th>
    </tr>
    <c:forEach var="country" items="${listSearch}">
        <tr>
        <td><c:out value="${country.id}"/></td>
        <td><c:out value="${country.name}"/></td>
        <td><c:out value="${country.email}"/></td>
        <td><c:out value="${country.country}"/></td>
        </tr>
    </c:forEach>
</table>
<a href="/users">Quay lai</a>
</body>
</html>
