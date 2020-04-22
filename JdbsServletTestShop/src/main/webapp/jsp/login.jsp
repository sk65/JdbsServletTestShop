<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>login</title>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
    <meta charset="utf-8">
  </head>
  <body>
    <div class="form-style-2">
        <form method="post" action="/login">
            <label for="name">User name
                <input class="input-field" type="text" id="name" name="name">
            </label>
            <label for="password">Password
                <input class="input-field" type="password" id="password" name="password">
            </label>
            <input type="submit" value="Sign Up">
        </form>
        <a href="/signUp"><h3>Регистрация</h3></a>
    </div>
  </body>
</html>
