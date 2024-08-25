<html>
<head>
    <title>Tool Detail</title>
</head>
<body>
    <h1>Tool Details</h1>
    <p>Tool Code: ${tool.toolCode}</p>
    <p>Tool Type: ${tool.toolType.name}</p>
    <p>Tool Brand: ${tool.brand.name}</p>
    <a href="${pageContext.request.contextPath}/tools/rent?toolCode=${tool.toolCode}">Rent This Tool?</a>
    <p><a href="${pageContext.request.contextPath}/tools">View Tools</a></p>
</body>
</html>
