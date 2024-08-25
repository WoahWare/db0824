<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Tool Types List</title>
</head>
<body>
    <h1>Available Tool Types And Their Charges </h1>
    <ul>
        <c:forEach var="toolTypeCharge" items="${toolTypeCharges}">
            <li><a href="${pageContext.request.contextPath}/toolTypeCharges/${toolTypeCharge.id}">${toolTypeCharge.toolType.name}</a></li>
        </c:forEach>
    </ul>
    <a href="/">Return to home page</a>
</body>
</html>

