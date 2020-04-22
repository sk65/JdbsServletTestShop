package dao.impl;


import dao.UserDao;
import models.Cart;
import models.Product;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UsersDaoJdbc implements UserDao {
    private Connection connection;

    public UsersDaoJdbc(String url, String username, String password, String driverClassName) throws ClassNotFoundException, SQLException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM my_shop.user");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String password = resultSet.getString("password");
                int phoneNumber = resultSet.getInt("phone_number");

                users.add(new User(id, name, surname, password, phoneNumber, new Cart(new HashMap<>())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> selectBuNameAndPassword(String name, String password) {
        Optional<User> userOptional = Optional.empty();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT *FROM my_shop.user where (name = '" + name + "' ) and (password = '" + password + "');");
            while (resultSet.next()) {

                if (resultSet.getString("name").equals(name) &&
                        resultSet.getString("password").equals(password)) {
                    int id = resultSet.getInt("id");
                    int phoneNumber = resultSet.getInt("phone_number");
                    String surname = resultSet.getString("surname");
                    userOptional = Optional.of(new User(id, name, surname, password, phoneNumber, new Cart(new HashMap<>())));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userOptional;
    }

    @Override
    public void updateName(int id, String newName) {
        String userNewName = String.valueOf(newName);
        String userId = String.valueOf(id);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE my_shop.user SET name  = (?) WHERE id = (?);");
            preparedStatement.setString(1, userNewName);
            preparedStatement.setString(2, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSurname(int id, String newSurname) {
        String userNewSurname = String.valueOf(newSurname);
        String userId = String.valueOf(id);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE my_shop.user SET surname  = (?) WHERE id = (?);");
            preparedStatement.setString(1, userNewSurname);
            preparedStatement.setString(2, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePassword(int id, String newPassword) {
        String userNewPassword = String.valueOf(newPassword);
        String userId = String.valueOf(id);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE my_shop.user SET password  = (?) WHERE id = (?);");
            preparedStatement.setString(1, userNewPassword);
            preparedStatement.setString(2, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePhoneNumber(int id, String newNumber) {
        String userNewNumber = String.valueOf(newNumber);
        String userId = String.valueOf(id);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE my_shop.user SET phone_number  = (?) WHERE id = (?);");
            preparedStatement.setString(1, userNewNumber);
            preparedStatement.setString(2, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> selectBuId(Integer id) {
        Optional<User> userOptional = Optional.empty();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM my_shop.user where id = " + id + ";");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String password = resultSet.getString("password");
                int phoneNumber = resultSet.getInt("phone_number");
                userOptional = Optional.of(new User(id, name, surname, password, phoneNumber, new Cart(new HashMap<>())));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userOptional;
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("INSERT INTO my_shop.user (name, surname,password,phone_number)" +
                                    " VALUES (?, ?, ?,?);");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, String.valueOf(user.getPhoneNumber()));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Integer id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(" DELETE FROM my_shop.user WHERE id = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
