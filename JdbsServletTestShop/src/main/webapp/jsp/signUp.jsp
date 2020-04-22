<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
  <head>
    <title>signUp</title>
     <link href="/css/styles.css" rel="stylesheet" type="text/css">
     <meta charset="utf-8">
  </head>
  <body>
    <div class="form-style-2">
        <div class="form-style-2-heading">
                Please Sign Up!
        </div>
          <form method="post" action="/signUp">
                  <label for="name">User name
                      <input class="input-field" type="text" id="name" name="name">
                  </label>
                   <label for="surname">User surname
                       <input class="input-field" type="text" id="surname" name="surname">
                   </label>

                  <label for="phoneNumber">Phone Number
                      <input class="input-field" type="text" id="phoneNumber" name="phoneNumber">
                  </label>
                  <label for="password">Password
                      <input class="input-field" type="passWord" id="password" name="password">
                  </label>
                  <input type="submit" value="Sign Up">
              </form>
      </div>
  </body>
</html>
