package com.excilys.formation.java.persistence;

import org.springframework.data.repository.CrudRepository;

import com.excilys.formation.java.model.User;

public interface UserDao extends CrudRepository<User, Long>{
  User findByUsername(String username);
}
