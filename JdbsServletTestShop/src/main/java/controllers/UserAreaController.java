package controllers;

import dao.UserDao;
import dao.impl.UsersDaoJdbc;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@WebServlet("/costumer_area")
public class UserAreaController extends HttpServlet {
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
        Cookie[] cookies = req.getCookies();
        String cookieUserId = "userId";
        Optional<User> userOptional = Optional.empty();
        User user;
        int userId;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieUserId.equals(cookie.getName())) {
                    userId = Integer.parseInt(cookie.getValue());
                    userOptional = userDao.selectBuId(userId);
                    break;
                }
            }
        }
        if (userOptional.isPresent()) {
            user = userOptional.get();
            req.setAttribute("user", user);
        }

        req.getServletContext().getRequestDispatcher("/jsp/costumerArea.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        changeUserParam(req);
        resp.sendRedirect("/costumer_area");
    }

    private void changeUserParam(HttpServletRequest req) {
        if (!(req.getParameter("userId") == null)) {
            int userId = Integer.parseInt(req.getParameter("userId"));

            if (!(req.getParameter("updateName") == null)) {
                String newName = req.getParameter("updateName");
                userDao.updateName(userId, newName);
            }
            if (!(req.getParameter("updateSurname") == null)) {
                String newSurname = req.getParameter("updateSurname");
                userDao.updateSurname(userId, newSurname);
            }
            if (!(req.getParameter("updatePassword") == null)) {
                String newPassword = req.getParameter("updatePassword");
                userDao.updatePassword(userId, newPassword);
            }
            if (!(req.getParameter("updatePhoneNumber") == null)) {
                String newPhoneNumber = req.getParameter("updatePhoneNumber");
                userDao.updatePhoneNumber(userId, newPhoneNumber);
            }
        }
    }
}
