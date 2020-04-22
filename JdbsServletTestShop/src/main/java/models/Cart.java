package models;

import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Cart {
    private Map<Product, Integer> products;
    private double totalAmount;
    private int totalQuantity;

    public Cart(Map<Product, Integer> products) {
        this.products = products;
        this.totalQuantity = getTotalQuantity();
        this.totalAmount = getTotalAmount();
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void upgradeProductQuantity(Product product, int quantity) {
        quantity = products.get(product) + quantity;
        if (quantity <= 0) {
            removeProduct(product.getId());
            return;
        }
        products.put(product, quantity);
    }

    public double getProductPrice(int id) {
        for (Product key : products.keySet()) {
            if (key.getId() == id) {
                return key.getPrice() * products.get(key);
            }
        }
        return 0;
    }

    public double getTotalAmount() {
        double counter = 0;
        for (Product key : products.keySet()) {
            counter += key.getPrice() * products.get(key);
        }

        //  int ss = products.keySet().stream().reduce(0, (acc, x) -> acc + (x.getPrice() * products.get(x)));
        return counter;
    }

    public int getTotalQuantity() {
        int count = 0;
        for (Product key : products.keySet()) {
            count += products.get(key);
        }
        //  Optional<Integer> ff = products.values().stream().reduce(Integer::sum);

        return count;
    }

    public void removeProduct(int id) {
        for (Product key : products.keySet()) {
            if (key.getId() == id) {
                products.remove(key);
                return;
            }
        }
         products.keySet().stream().filter(x -> x.getId() == id).map(x -> products.remove(x)).forEach(System.out::println);
    }
}
