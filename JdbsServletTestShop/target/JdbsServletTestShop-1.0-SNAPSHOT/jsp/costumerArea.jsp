<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
  <head>
    <title>costumerArea</title>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
    <meta charset="utf-8">
  </head>
  <body>
  <h2>Hello ${user.name}</h2>
  <div class="form-style-2">
          <table>
              <tr>
                  <th>User name</th>
                  <th>User surname</th>
                  <th>User password</th>
                  <th>User phoneNumber</th>
              </tr>
              <tr>
               <td>${user.name}</td>
               <td>${user.surname}</td>
               <td>${user.password}</td>
               <td>${user.phoneNumber}</td>
              </tr>
              <tr>
               <td>
                <form method="post" action="/costumer_area">
                   <input type="hidden"  name="userId" value=${user.id}>
                   <input class="input-field" type="text"  name="updateName" >
                   <input class="input-field" type="submit" class="input-field" value="change name">
                </form>
                </td>
              <td>
                <form method="post" action="/costumer_area">
                  <input type="hidden"  name="userId" value=${user.id}>
                  <input class="input-field" type="text"  name="updateSurname">
                  <input class="input-field" type="submit" class="input-field" value="change surname">
                </form>
              </td>
              <td>
                <form method="post" action="/costumer_area">
                  <input type="hidden"  name="userId" value=${user.id}>
                  <input class="input-field" type="text"  name="updatePassword">
                  <input class="input-field" type="submit" class="input-field" value="change password">
                </form>
              </td>
              <td>
                <form method="post" action="/costumer_area">
                  <input type="hidden"  name="userId" value=${user.id}>
                  <input class="input-field" type="text"  name="updatePhoneNumber">
                  <input class="input-field" type="submit" class="input-field" value="change number">
                </form>
              </td>
              </tr>
          </table>
      </div>
      <div class="form-style-2">
      <h2><a href="/main">Main</a></h2>
       <h2><a href="/user_cart">Cart</a></h2>
      </div>
  </body>
</html>
