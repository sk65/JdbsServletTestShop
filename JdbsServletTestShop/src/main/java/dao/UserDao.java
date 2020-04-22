package dao;

import models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User> {
    List<User> selectAll();

    Optional<User> selectBuNameAndPassword(String name, String password);

    void updateName(int id, String newName);

    void updateSurname(int id, String newName);

    void updatePassword(int id, String newPassword);

    void updatePhoneNumber(int id, String newNumber);


}
