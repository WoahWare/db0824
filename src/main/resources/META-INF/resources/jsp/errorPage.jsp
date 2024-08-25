<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h2>Error Occurred:</h2>
    <p>${errorMessage}</p>
    <p> <button onclick="history.back()">Go Back to Previous Page</button> </p>
    <p> <a href="${pageContext.request.contextPath}/">Go back to Home Page</a>  </p>
</body>
</html>
