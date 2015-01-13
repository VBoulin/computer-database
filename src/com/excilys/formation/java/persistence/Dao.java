package com.excilys.formation.java.persistence;

import java.util.List;

public interface Dao<T> {
  void create(T o);

  T getOne(Long id);

  List<T> getAll();

  void update(T o);

  void delete(Long id);
}
