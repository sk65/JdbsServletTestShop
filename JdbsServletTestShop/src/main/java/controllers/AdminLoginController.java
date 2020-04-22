package controllers;

import dao.AdminDao;
import dao.impl.AdminDaoJdbc;
import models.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@WebServlet("/admin_login")
public class AdminLoginController extends HttpServlet {
    private AdminDao adminDao;

    @Override
    public void init() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String driverClassName = properties.getProperty("db.driverClassName");
            adminDao = new AdminDaoJdbc(dbUrl, dbUsername, dbPassword, driverClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/adminLogin.jsp").forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        Optional<Admin> adminOptional = adminDao.selectBuNameAndPassword(name, password);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            HttpSession session = req.getSession(false);
            session.setAttribute("adminSession", "admin");
            session.setAttribute("admin", admin);
            resp.sendRedirect("/admin");
        } else {
            resp.sendRedirect("/admin_login");
        }

    }
}
