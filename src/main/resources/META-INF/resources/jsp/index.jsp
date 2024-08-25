<html>
<head>
    <title>Tool Rental Service</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 800px;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .nav {
            text-align: center;
            margin-top: 30px;
        }
        .nav a {
            display: inline-block;
            margin: 10px 20px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .nav a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to the Tool Rental Service</h1>
        <center><p>Manage and rent tools with ease.</p></center>
        
        <div class="nav">
            <a href="${pageContext.request.contextPath}/tools">View Tools</a>
            <a href="${pageContext.request.contextPath}/toolTypeCharges">View Tool Types</a>
            <a href="${pageContext.request.contextPath}/tools/rent">Rent Tools</a>
            <a href="${pageContext.request.contextPath}/brands">View Brands</a>
            <a href="${pageContext.request.contextPath}/holidays">View Rental Holidays</a>
            <!-- Add more navigation links as needed -->
        </div>
    </div>
</body>
</html>
