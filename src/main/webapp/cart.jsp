<%--
  Created by IntelliJ IDEA.
  User: LeFat
  Date: 9/8/2026
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css">
</head>
<body>
    <h1>YOUR CART</h1>
    <table>
        <thead>
            <tr>
                <th>Quantity</th>
                <th>Description</th>
                <th>Price</th>
                <th>Amount</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>
                        <form action="cart" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="ProductID" value="${item.product.productID}">
                            <input type="text" name="quantity" value="${item.quantity}">
                            <input type="submit" value="Update">
                        </form>
                    </td>
                    <td>${item.product.description}</td>
                    <td>$${item.product.price}</td>
                    <td>$${item.totalLineItemPrice}</td>
                    <td>
                        <form action="cart" method="post">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="ProductID" value="${item.product.productID}">
                            <input type="submit" value="Remove Item">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <p><b>To change the quantity</b>, enter the new quantity and click on the Update button.</p>

    <form action="index.jsp" method="get">
        <input type="submit" value="Continue Shopping">
    </form>
    <form action="checkout" method="post">
        <input type="submit" value="Checkout">
    </form>
</body>
</html>
