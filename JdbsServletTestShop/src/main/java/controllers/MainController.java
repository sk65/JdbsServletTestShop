package controllers;

import dao.ProductDao;
import dao.impl.ProductDaoJdbc;
import dao.UserDao;
import dao.impl.UsersDaoJdbc;
import models.Cart;
import models.Product;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@WebServlet("/main")
public class MainController extends HttpServlet {
    private UserDao userDao;
    private ProductDao productDao;


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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productDao.SelectAll();
        User user = initUser(req);

        Optional<String> cartOptional = getCookieValue(req, "cart");
        if (cartOptional.isPresent()) {
            if (!cartOptional.get().equals("")) {
                HashMap<Product, Integer> cart = stringToMap(cartOptional.get());
                user.setUserCart(new Cart(cart));
            }
        }
        req.setAttribute("user", user);
        req.setAttribute("productsFromServer", products);
        req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String templeCart = req.getParameter("templeCart");
        Optional<String> optional = getCookieValue(req, "cart");

        if (optional.isPresent()) {
            resp.addCookie(new Cookie("cart", addToCart(req)));
        } else {
            resp.addCookie(new Cookie("cart", templeCart));
        }
        resp.sendRedirect("/main");
    }

    private HashMap<Product, Integer> stringToMap(String string) {
        if (string == null) return null;
        HashMap<Product, Integer> cart = new HashMap<>();
        String[] pairs = string.split("a");
        for (String pair : pairs) {
            String[] keyValue = pair.split("A");
            Optional<Product> product = productDao.selectBuId(Integer.parseInt(keyValue[0]));
            product.ifPresent(value -> cart.put(value, Integer.valueOf(keyValue[1])));
        }
        return cart;
    }

    private String addToCart(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        Optional<String> optional = getCookieValue(req, "cart");
        if (optional.isPresent()) {
            String cart = optional.get();

            String templeCart = req.getParameter("templeCart");
            templeCart = templeCart.replace(templeCart.substring(templeCart.length() - 1), "");

            String[] pairs = cart.split("a");
            String[] kvTemple = templeCart.split("A");

            for (int i = 0; i < pairs.length; i++) {
                String[] keyValue = pairs[i].split("A");
                if (kvTemple[0].equals(keyValue[0])) {
                    pairs[i] = kvTemple[0] + "A" + (Integer.parseInt(keyValue[1]) + Integer.parseInt(kvTemple[1]));
                    break;
                }
                if (i == pairs.length - 1) {
                    return cart + templeCart + "a";
                }
            }
            for (String pair : pairs) {
                sb.append(pair).append("a");
            }
        }
        return sb.toString();

    }

    private Optional<String> getCookieValue(HttpServletRequest req, String value) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (value.equals(cookie.getName())) {
                    return Optional.of(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }

    private User initUser(HttpServletRequest req) {
        User user = new User("Guest");
        Optional<String> userIdOptional = getCookieValue(req, "userId");
        if (userIdOptional.isPresent()) {
            Optional<User> userOptional = userDao.selectBuId(Integer.parseInt(userIdOptional.get()));
            if (userOptional.isPresent()) {
                user = userOptional.get();
            }
        }
        return user;
    }


}
