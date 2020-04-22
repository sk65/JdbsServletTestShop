package controllers;

import dao.UserDao;
import dao.impl.UsersDaoJdbc;
import models.Cart;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;

@WebServlet("/signUp")
public class SignUpController extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String driverClassName = properties.getProperty("db.driverClassName");
            userDao = new UsersDaoJdbc(dbUrl, dbUsername, dbPassword, driverClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/signUp.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String password = req.getParameter("password");
        int phoneNumber = Integer.parseInt(req.getParameter("phoneNumber"));
        User user = new User(name, surname, password, phoneNumber, new Cart(new HashMap<>()));
        userDao.save(user);
        Optional<User> optional = userDao.selectBuNameAndPassword(name, password);
        if (optional.isPresent()) {
            user = optional.get();
            Cookie userIdCookie = new Cookie("userId", String.valueOf(user.getId()));
            resp.addCookie(userIdCookie);
            resp.sendRedirect("/main");
        }else {
            resp.sendRedirect("/login");
        }


    }
}
