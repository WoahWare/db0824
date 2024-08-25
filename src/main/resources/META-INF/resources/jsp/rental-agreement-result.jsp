<html>
<head>
    <title>Rental Agreement</title>
</head>
<body>
    <h1>Rental Agreement Details:</h1>
    <p>Tool Code: ${rentalAgreement.toolCode}</p>
    <p>Tool Type: ${rentalAgreement.toolType}</p>
    <p>Tool Brand: ${rentalAgreement.toolBrand}</p>
    <p>Rental Days: ${rentalAgreement.rentalDays}</p>
    <p>Checkout Date: ${rentalAgreement.checkOutDate} </p>
    <p>Due Date: ${rentalAgreement.dueDate}</p>
    <p>Daily Rental Charge: $${rentalAgreement.dailyRentalCharge}</p>
    <p>Charge Days: ${rentalAgreement.chargeDays}</p>
    <p>Pre-Discount Charge: $${rentalAgreement.preDiscountCharge}</p>
    <p>Discount Percent: ${rentalAgreement.discountPercent}%</p>
    <p>Discount Amount: $${rentalAgreement.discountAmount}</p>
    <p>Final Charge: $${rentalAgreement.finalCharge}</p>
    <a href="${pageContext.request.contextPath}/tools">Back to Tool List</a>
    <p><a href="/">Return to home page</a></p>
</body>
</html>
