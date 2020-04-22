package dao;

import models.User;

import java.util.Optional;

public interface CrudDao <T>{
    Optional<T> selectBuId(Integer id);
  //  User selectBuId(Integer id);

    void save(T model);

    void update(T model);

    void delete(Integer id);
}
