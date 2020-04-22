<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
    <meta charset="utf-8">
    <title>admin</title>
  </head>
  <body>
   <div class="form-style-2">
  <h1>Hello Admin</h1>
  <h3>Change password</h3>
  <form method="post" action="/admin">
     <input class="input-field" type="password"  name="oldPassword" placeholder="old password">
     <input class="input-field" type="password"  name="newPassword" placeholder="new password">
     <input type="submit" class="input-field" value="set new password">
    </div>
  </form>
   <div class="form-style-2">
        <table>
            <tr>
                <th>User id</th>
                <th>User name</th>
                <th>User surname</th>
                <th>User phoneNumber</th>
                <th>Delete User</th>

            </tr>
            <c:forEach items="${usersFromServer}" var="user">
                <tr>
                    <td>${user.id} </td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.phoneNumber}</td>
                    <td>
                     <form method="post" action="/admin">
                       <input type="hidden"  name="deleteUser" value=${user.id}>
                       <input type="submit" class="input-field" value="delete">
                     </form>
                    </td>

                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="form-style-2">
            <table>
                <tr>
                    <th>Product id</th>
                    <th>Product name</th>
                    <th>Product price</th>
                    <th>Product stock availability</th>
                    <th>Delete product</th>
                    <th>Set new price</th>
                    <th>Set new Availability</th>
                    <th>Set new Name</th>
                </tr>
                <c:forEach items="${productsFromServer}" var="product">
                    <tr>
                        <td>${product.id} </td>
                        <td>${product.name}</td>
                        <td>${product.price}</td>
                        <td>${product.stock_availability}</td>
                        <td>
                             <form method="post" action="/admin">
                                <input type="hidden"  name="productId" value=${product.id}>
                                <input type="hidden"  name="deleteProduct" value=${product.id}>
                                <input type="submit" class="input-field" value="delete">
                             </form>

                          </td>
                          <td>
                              <form method="post" action="/admin">
                                  <input type="hidden"  name="productId" value=${product.id}>
                                  <input class="input-field" type="text" name="updatePrice">
                                  <input type="submit" class="input-field" value="set New Price">
                               </form>
                          </td>
                           <td>
                                <form method="post" action="/admin">
                                     <input type="hidden"  name="productId" value=${product.id}>
                                     <input class="input-field" type="text" name="updateAvailability">
                                     <input type="submit" class="input-field" value="set New Availability">
                                </form>
                            </td>
                            <td>
                                <form method="post" action="/admin">
                                      <input type="hidden"  name="productId" value=${product.id}>
                                      <input class="input-field" type="text" name="updateName">
                                      <input type="submit" class="input-field" value="set New Name">
                                </form>
                            </td>
                    </tr>
                </c:forEach>
            </table>
            <form method="post" action="/admin">
            <input type="hidden"  name="createNewProduct" value="createNewProduct">
             <input class="input-field" type="text" name="name" placeholder="name">
             <input class="input-field" type="text" name="price"placeholder="price">
             <input class="input-field" type="text" name="stockAvailability"placeholder="stockAvailability">
             <input type="submit" class="input-field" value="create a new product">
            </form>
        </div>
  </body>
</html>
