package dao;

import models.Product;

import java.util.List;

public interface ProductDao extends CrudDao<Product> {
    List<Product> SelectAll();

    void setPrice(int price, int id);

    void setNewAvailability(int newAvailability, int id);

    void setNewName(String newName, int productIdId);

    void createNewProduct(String price, String name, int stockAvailability);

    void UpdateProductQuantity(int id, int newQuantity);
}
