<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>main</title>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
        <meta charset="utf-8">
  </head>
  <body>
  <h1>Hello ${user.name}</h1>
     <div class="form-style-2">
                <table>
                    <tr>
                        <th>Product name</th>
                        <th>Product price</th>
                        <th>Product availability</th>
                    </tr>
                    <c:forEach items="${productsFromServer}" var="product">
                        <tr>
                            <td>${product.name}</td>
                            <td>${product.price}</td>
                            <td>${product.stock_availability}</td>
                            <td>
                             <form method="post" action="/main">
                                <input type="hidden"  name="templeCart" value="${product.id}A1a">
                                <input type="submit" class="input-field" value="add to cart">
                              </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="form-style-2">
              <h3>product quantity: ${user.userCart.totalQuantity}</h3>
              <h3>total amount: ${user.userCart.totalAmount}</h3>
            </div>
            <h2><a href="/user_cart">Cart</a></h2>
            <h2><a href="/costumer_area">Costumer area</a></h2>
  </body>
</html>
