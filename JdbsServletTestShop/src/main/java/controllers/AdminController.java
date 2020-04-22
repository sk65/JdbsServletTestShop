package controllers;

import dao.AdminDao;
import dao.ProductDao;
import dao.impl.AdminDaoJdbc;
import dao.impl.ProductDaoJdbc;
import dao.UserDao;
import dao.impl.UsersDaoJdbc;
import models.Admin;
import models.Product;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    private UserDao userDao;
    private ProductDao productDao;
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
            userDao = new UsersDaoJdbc(dbUrl, dbUsername, dbPassword, driverClassName);
            productDao = new ProductDaoJdbc(dbUrl, dbUsername, dbPassword, driverClassName);
            adminDao = new AdminDaoJdbc(dbUrl, dbUsername, dbPassword, driverClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userDao.selectAll();
        List<Product> products = productDao.SelectAll();

        req.setAttribute("productsFromServer", products);
        req.setAttribute("usersFromServer", users);

        req.getServletContext().getRequestDispatcher("/jsp/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        changeProductParam(req);
        changeUserParam(req);
        createProduct(req);
        changeAdminPassword(req);
        resp.sendRedirect("/admin");
    }

    private void changeAdminPassword(HttpServletRequest req) {
        Admin admin = (Admin) req.getSession(false).getAttribute("admin");
        if (!(req.getParameter("newPassword") == null) &&
                !(admin == null)) {
            String newPassword = req.getParameter("newPassword");
            String oldPassword = req.getParameter("oldPassword");
            if (admin.getPassword().equals(oldPassword)) {
                adminDao.changePassword(admin.getId(), newPassword);
            }

        }

    }

    private void createProduct(HttpServletRequest req) {
        if (req.getParameter("createNewProduct") != null &&
                req.getParameter("price") != null &&
                req.getParameter("name") != null &&
                req.getParameter("stockAvailability") != null) {

            int stockAvailability = Integer.parseInt(req.getParameter("stockAvailability"));
            String price = req.getParameter("price");
            String name = req.getParameter("name");
            productDao.createNewProduct(price, name, stockAvailability);

        }
    }

    private void changeUserParam(HttpServletRequest req) {
        if (!(req.getParameter("deleteUser") == null)) {
            int userId = Integer.parseInt(req.getParameter("deleteUser"));
            userDao.delete(userId);
        }
    }

    private void changeProductParam(HttpServletRequest req) {
        if (!(req.getParameter("productId") == null)) {

            int productId = Integer.parseInt(req.getParameter("productId"));

            if (!(req.getParameter("deleteProduct") == null)) {
                productDao.delete(productId);
            }

            if (!(req.getParameter("updatePrice") == null)) {
                int newPrice = Integer.parseInt(req.getParameter("updatePrice"));
                productDao.setPrice(newPrice, productId);
            }

            if (!(req.getParameter("updateAvailability") == null)) {
                int newAvailability = Integer.parseInt(req.getParameter("updateAvailability"));
                productDao.setNewAvailability(newAvailability, productId);
            }

            if (!(req.getParameter("updateName") == null)) {
                String newName = (req.getParameter("updateName"));
                productDao.setNewName(newName, productId);
            }
        }
    }

}

