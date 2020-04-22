package controllers;

import dao.UserDao;
import dao.impl.UsersDaoJdbc;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@WebServlet("/login")
public class LoginController extends HttpServlet {
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
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Optional<User> userOptional = userDao.selectBuNameAndPassword(name, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Cookie userIdCookie = new Cookie("userId", String.valueOf(user.getId()));
            resp.addCookie(userIdCookie);
            resp.sendRedirect("/main");

        } else {
            resp.sendRedirect("/login");
        }

    }
}
