<%--
  Created by IntelliJ IDEA.
  User: LeFat
  Date: 9/10/2026
  Time: 14:38
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
<h2>xi</h2>
<table>
    <tr>
        <th>Song Title</th>
        <th>Audio Format</th>
    </tr>
    <tr>
        <td>Ascension to Heaven</td>
        <td>
            <a href="${pageContext.request.contextPath}/music-resources/${productCode}/Ascension-To-Heaven.mp3">MP3</a>
        </td>
    </tr>
    <tr>
        <td>Freedom Dive</td>
        <td>
            <a href="${pageContext.request.contextPath}/music-resources/${productCode}/Freedom-Dive.mp3">MP3</a>
        </td>
    </tr>
</table>
</body>
</html>
