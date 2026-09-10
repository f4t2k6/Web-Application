<%--
  Created by IntelliJ IDEA.
  User: LeFat
  Date: 9/10/2026
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>USER REGISTRATION</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css">
</head>
<body>
<h1>Downloads</h1>
<h2>PSYQUI</h2>
<table>
    <tr>
        <th>Song Title</th>
        <th>Audio Format</th>
    </tr>
    <tr>
        <td>Enroll</td>
        <td>
            <a href="${pageContext.request.contextPath}/music-resources/${productCode}/Enroll.mp3">MP3</a>
        </td>
    </tr>
    <tr>
        <td>What Is Vibe</td>
        <td>
            <a href="${pageContext.request.contextPath}/music-resources/${productCode}/What-Is-Vibe.mp3">MP3</a>
        </td>
    </tr>
</table>
</body>
</html>
