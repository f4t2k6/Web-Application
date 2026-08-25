<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Survey - Result</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
        }
        .container {
            width: 500px;
            margin: 30px auto;
            background-color: #ffffff;
            padding: 20px 30px;
            border: 1px solid #ccc;
            border-radius: 6px;
            text-align: center;
        }
        h1 {
            color: #2e6da4;
        }
        .info {
            text-align: left;
            margin-top: 20px;
        }
        .info p {
            margin: 6px 0;
        }
        .info span {
            font-weight: bold;
        }
        a.back {
            display: inline-block;
            margin-top: 20px;
            color: #2e6da4;
        }
    </style>
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

        <a class="back" href="index.html">Quay lại trang khảo sát</a>
    </div>
</body>
</html>
