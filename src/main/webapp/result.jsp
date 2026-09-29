<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Survey - Result</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css">
</head>
<body>
    <div class="container">
        <h1>Submit thành công!</h1>
        <p>Cảm ơn bạn đã hoàn thành khảo sát.</p>

        <div class="info">
            <p><span>Họ tên:</span> ${fullName}</p>
            <p><span>Email:</span> ${email}</p>
            <p><span>Ngày sinh:</span> ${birthDate}</p>
            <p><span>Biết đến qua:</span> ${hearAbout}</p>
            <p><span>Muốn nhận thông báo:</span> ${receiveAnnouncements}</p>
        </div>

        <c:if test="${not empty emailMessage}">
            <p><c:out value="${emailMessage}"/></p>
        </c:if>

        <a class="back" href="index.jsp">Quay lại trang khảo sát</a>
    </div>
</body>
</html>
