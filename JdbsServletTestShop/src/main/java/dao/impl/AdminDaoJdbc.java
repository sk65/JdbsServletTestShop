package dao.impl;

import dao.AdminDao;
import models.Admin;

import java.sql.*;
import java.util.Optional;

public class AdminDaoJdbc implements AdminDao {
    private Connection connection;

    public AdminDaoJdbc(String url, String username, String password, String driverClassName) throws ClassNotFoundException, SQLException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public Optional<Admin> selectBuId(int id) {
        Optional<Admin> adminOptional = Optional.empty();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM my_shop.admin where id = " + id + ";");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                adminOptional = Optional.of(new Admin(id, name, password));
                return adminOptional;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminOptional;
    }

    @Override
    public void changePassword(int id, String newPassword) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("UPDATE my_shop.admin SET password = ? WHERE (id = ?);");
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, String.valueOf(id));
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Admin> selectBuNameAndPassword(String name, String password) {
        Optional<Admin> adminOptional = Optional.empty();
        try {
            ResultSet resultSet = connection.createStatement().
                    executeQuery("SELECT *FROM my_shop.admin where (name = '" + name + "' ) and (password = '" + password + "');");
            while (resultSet.next()) {
                if (resultSet.getString("name").equals(name) &&
                        resultSet.getString("password").equals(password)) {

                    int id = resultSet.getInt("id");
                    adminOptional = Optional.of(new Admin(id, name, password));
                    return adminOptional;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adminOptional;
    }
}
