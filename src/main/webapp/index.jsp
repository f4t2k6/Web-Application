<%--
  Created by IntelliJ IDEA.
  User: LeFat
  Date: 9/9/2026
  Time: 07:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>CD Store</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css">
</head>
<body>
<h1>CD LIST</h1>
<table>
    <thead>
        <tr>
            <th>Description</th>
            <th>Price</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>86 (the band) - True Life Songs and Pictures</td>
            <td>$14.95</td>
            <td>
                <form action="cart" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="ProductID" value="001">
                    <input type="hidden" name="description" value="86 (the band) - True Life Songs and Pictures">
                    <input type="hidden" name="price" value="14.95">
                    <input type="submit" value="Add to Cart">
                </form>
            </td>
        </tr>
        <tr>
            <td>Paddlefoot - The first CD</td>
            <td>$12.95</td>
            <td>
                <form action="cart" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="ProductID" value="002">
                    <input type="hidden" name="description" value="Paddlefoot - The first CD">
                    <input type="hidden" name="price" value="12.95">
                    <input type="submit" value="Add to Cart">
                </form>
            </td>
        </tr>
        <tr>
            <td>Joe Rut - Genuine Wood Grained Finish</td>
            <td>$14.95</td>
            <td>
                <form action="cart" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="ProductID" value="003">
                    <input type="hidden" name="description" value="Joe Rut - Genuine Wood Grained Finish">
                    <input type="hidden" name="price" value="14.95">
                    <input type="submit" value="Add to Cart">
                </form>
            </td>
        </tr>
    </tbody>
</table>
</body>
</html>
