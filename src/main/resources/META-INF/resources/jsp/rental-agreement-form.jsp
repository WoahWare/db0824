<html>
<head>
    <title>Rent Tool</title>
</head>
<body>
    <h1>Rent Tool</h1>
    <form action="${pageContext.request.contextPath}/tools/rent" method="post">
        <label for="toolCode">Tool Code:</label>
        <input type="text" id="toolCode" name="toolCode" value="${rentalAgreement.toolCode}" required><br>

        <label for="checkoutDate">Checkout Date:</label>
        <input type="date" id="checkOutDate" name="checkOutDate" value="${rentalAgreement.checkOutDate}" required><br>

        <label for="rentalDayCount">Rental Days:</label>
        <input type="number" id="rentalDayCount" name="rentalDayCount" value="${rentalAgreement.rentalDayCount}" required><br>

        <label for="discountPercent">Discount Percent:</label>
        <input type="number" id="discountPercent" name="discountPercent" value="${rentalAgreement.discountPercent}" required><br>

        <input type="submit" value="Generate Rental Agreement">
    </form>
    <a href="${pageContext.request.contextPath}/tools">Back to Tool List</a>   
    <p><a href="/">Return to home page</a></p>
</body>
</html>
