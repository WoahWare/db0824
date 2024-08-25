<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Brand List</title>
</head>
<body>
    <h1>Brands That We Carry</h1>
    <ul>
        <c:forEach var="brand" items="${brands}">
            <li><a href="${pageContext.request.contextPath}/brands/${brand.id}">${brand.name}</a></li>
        </c:forEach>
    </ul>
    <a href="/">Return to home page</a>
</body>
</html>
