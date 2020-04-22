package controllers;

import dao.ProductDao;
import dao.UserDao;
import dao.impl.ProductDaoJdbc;
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
import java.util.stream.Collectors;

@WebServlet("/user_cart")
public class UserCartController extends HttpServlet {
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
        Optional<String> cartOptional = getCookieValue(req, "cart");
        User user = initUser(req);

        if (cartOptional.isPresent()) {
            if (!cartOptional.get().equals("")) {
                HashMap<Product, Integer> cart = stringToMap(cartOptional.get());
                user.setUserCart(new Cart(cart));
            }
        }
        if (user.getUserCart() != null) {
            Map<Product, Integer> map = user.getUserCart().getProducts();
            req.setAttribute("cart", map);
        }
        req.setAttribute("user", user);

        req.getRequestDispatcher("jsp/userCart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = initUser(req);
        Optional<String> cartOptional = getCookieValue(req, "cart");
        cartOptional.ifPresent(s -> user.setUserCart(new Cart(stringToMap(s))));

        Map<Product, Integer> map = user.getUserCart().getProducts();
        Optional<Product> productOptional;
        if (!(req.getParameter("productId") == null)) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            if (!(req.getParameter("delete") == null)) {
                user.getUserCart().removeProduct(productId);
            }
            if (!(req.getParameter("update") == null)) {
                int quantity = Integer.parseInt(req.getParameter("update"));
                productOptional = productDao.selectBuId(productId);
                productOptional.ifPresent(product -> user.getUserCart().upgradeProductQuantity(product, quantity));
            }

        }

        resp.addCookie(new Cookie("cart", mapToString(map)));
        resp.sendRedirect("/user_cart");
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

    private String mapToString(Map<Product, Integer> map) {
        return map.keySet().stream()
                .map(key -> key.getId() + "A" + map.get(key))
                .collect(Collectors.joining("a"));

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
