package filter;

import dao.UserDao;
import dao.impl.UsersDaoJdbc;
import models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@WebFilter("/costumer_area")
public class UserAreaFilter implements Filter {
    private UserDao userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        initUser(req);
        Cookie[] cookies = req.getCookies();
        String cookieUserId = "userId";
        Optional<User> userOptional = Optional.empty();
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
        if (userOptional.isEmpty()) {
            req.getServletContext().getRequestDispatcher("/login").forward(req, resp);
        }
        filterChain.doFilter(req, resp);

    }


    @Override
    public void destroy() {

    }

    void initUser(HttpServletRequest req) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(req.getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String driverClassName = properties.getProperty("db.driverClassName");
            userDao = new UsersDaoJdbc(dbUrl, dbUsername, dbPassword, driverClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
