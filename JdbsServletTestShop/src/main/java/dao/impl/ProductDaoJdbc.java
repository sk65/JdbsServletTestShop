package dao.impl;

import dao.ProductDao;
import models.Cart;
import models.Product;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoJdbc implements ProductDao {
    private Connection connection;

    public ProductDaoJdbc(String url, String username, String password, String driverClassName) throws ClassNotFoundException, SQLException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public List<Product> SelectAll() {
        List<Product> products = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM my_shop.product");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int stockAvailability = resultSet.getInt("stock_availability");
                products.add(new Product(id, name, price, stockAvailability));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void setPrice(int price, int id) {
        String productPrice = String.valueOf(price);
        String productId = String.valueOf(id);

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE my_shop.product SET price  = (?) WHERE id = (?);");
            preparedStatement.setString(1, productPrice);
            preparedStatement.setString(2, productId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNewAvailability(int newAvailability, int id) {
        String ProductNewAvailability = String.valueOf(newAvailability);
        String productId = String.valueOf(id);

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE my_shop.product SET stock_availability  = (?) WHERE id = (?);");
            preparedStatement.setString(1, ProductNewAvailability);
            preparedStatement.setString(2, productId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNewName(String newName, int id) {
        String productNewName = String.valueOf(newName);
        String productId = String.valueOf(id);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE my_shop.product SET name  = (?) WHERE id = (?);");
            preparedStatement.setString(1, productNewName);
            preparedStatement.setString(2, productId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createNewProduct(String price, String name, int stockAvailability) {
        String productStockAvailability = String.valueOf(stockAvailability);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("INSERT INTO my_shop.product (name, price,stock_availability)" +
                                    " VALUES (?, ?, ?);");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, price);
            preparedStatement.setString(3, productStockAvailability);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void UpdateProductQuantity(int id, int newQuantity) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE my_shop.product SET stock_availability = " + newQuantity + " WHERE (id = " + id + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Product> selectBuId(Integer id) {
        Optional<Product> productOptional = Optional.empty();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM my_shop.product where id = " + id + ";");
            while (resultSet.next()) {
                int price = resultSet.getInt("price");
                String name = resultSet.getString("name");
                int stockAvailability = resultSet.getInt("stock_availability");
                productOptional = Optional.of(new Product(id, name, price, stockAvailability));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productOptional;
    }

    @Override
    public void save(Product model) {

    }

    @Override
    public void update(Product model) {

    }

    @Override
    public void delete(Integer id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(" DELETE FROM my_shop.product WHERE id = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
