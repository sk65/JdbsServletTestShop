package models;

public class User {
    private int id;
    private String name;
    private String surname;
    private String password;
    private int phoneNumber;

    private Cart userCart;


    public void setUserCart(Cart userCart) {
        this.userCart = userCart;
    }

    public User(int id, String name, String surname, String password, int phoneNumber, Cart cart) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userCart = cart;

    }

    public User(String name, String surname, String password, int phoneNumber, Cart cart) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userCart = cart;

    }

    public User(String name) {
        this.name = name;

    }

    public Cart getUserCart() {
        return userCart;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
