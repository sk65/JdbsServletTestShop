package models;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private int price;
    private int stock_availability;

    public Product(int id, String name, int price, int stock_availability) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock_availability = stock_availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock_availability() {
        return stock_availability;
    }

    public void setStock_availability(int stock_availability) {
        this.stock_availability = stock_availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                price == product.price &&
                stock_availability == product.stock_availability &&
                name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, stock_availability);
    }

    @Override
    public String toString() {
        return name + "";
    }
}
