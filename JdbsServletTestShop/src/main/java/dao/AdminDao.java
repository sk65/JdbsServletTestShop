package dao;

import models.Admin;

import java.util.Optional;

public interface AdminDao {
    Optional<Admin> selectBuId(int id);

    void changePassword(int id, String newPassword);

    Optional<Admin> selectBuNameAndPassword(String name, String password);
}
