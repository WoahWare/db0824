<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Holiday List</title>
</head>
<body>
    <h1>All Rental Holidays</h1>
    <ul>
        <c:forEach var="holiday" items="${holidays}">
            <li><a href="${pageContext.request.contextPath}/holidays/${holiday.id}">${holiday.name}</a></li>
        </c:forEach>
    </ul>
    <a href="/">Return to home page</a>
</body>
</html>
