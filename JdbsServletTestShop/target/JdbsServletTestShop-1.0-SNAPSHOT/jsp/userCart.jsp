<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>UserCart</title>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
    <meta charset="utf-8">
  </head>
  <body>
  <div class="form-style-2">
  <h1>Hello ${user.name}</h1>

   <table>
    <tr>
     <th>Name</th>
     <th>Quantity</th>
     <th>Price</th>
     <th>Total price</th>
     <th>Remove</th>
    </tr>
      <c:forEach items="${cart}" var="cart">
      <tr>
       <td> ${cart.key.name}</td>
       <td>
        <form method="post" action="/user_cart">
         <%-- <input type="text" name="productId" placeholder=${cart.value} pattern="[0-9]">--%>
        <%--   <input type="submit" name="remove" value="removeProduct">--%>
        ${cart.value}
        </form>
       </td>
       <td> ${cart.key.price}</td>
       <td> ${cart.key.price*cart.value}</td>
       <td>
        <form method="post" action="/user_cart">
          <input type="hidden" name="productId" value=${cart.key.id}>
          <input type="submit" name="delete" value="deleteProduct">
        </form>
       </td>
      <td>
        <form method="post" action="/user_cart">
          <input type="hidden" name="productId" value=${cart.key.id}>
          <input type="hidden" name="update" value="1">
          <input type="submit"  value="+">
         </form>
       </td>
       <td>
        <form method="post" action="/user_cart">
          <input type="hidden" name="productId" value=${cart.key.id}>
          <input type="hidden" name="update" value="-1">
          <input type="submit"  value="-">
        </form>
       </td>
      </tr>
      </c:forEach>
    </table>
  <div class="form-style-2">
   <h3>product quantity: ${user.userCart.totalQuantity}</h3>
   <h3>total Amount: ${user.userCart.totalAmount}</h3>
   </div>
  <h2><a href="/main">Main</a></h2>
  <h2><a href="/costumer_area">Costumer area</a></h2>
  </div>
  </body>
</html>
