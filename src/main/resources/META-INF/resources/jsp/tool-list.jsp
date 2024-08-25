<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Tool List</title>
</head>
<body>
    <h1>Available Tools </h1>
    <ul>
        <c:forEach var="tool" items="${tools}">
            <li><a href="${pageContext.request.contextPath}/tools/${tool.id}">${tool.toolCode}</a></li>
        </c:forEach>
    </ul>
    <a href="/">Return to home page</a>
</body>
</html>
