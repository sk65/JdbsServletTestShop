<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
  <head>
    <title>test</title>
  </head>
  <body>
   <h1>${map}</h1>
  <form method="post" action="/test">
  <input type="hidden"  name="templeCart" value="9A1a">
  <input type="submit" class="input-field" value="add to cart">
   </form>
   <form method="post" action="/test">
     <input type="hidden"  name="templeCart" value="4A1a">
     <input type="submit" class="input-field" value="add to cart">
      </form>
    <form method="post" action="/test">
      <input type="hidden"  name="templeCart" value="3A1a">
      <input type="submit" class="input-field" value="add to cart">
       </form>
  </body>
</html>
