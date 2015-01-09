package com.excilys.formation.java.persistence;

import java.util.List;

public interface DAO<T> {
	public void create(T object);;
	public T retrieveOne(Long id);
	public List<T> retrieveAll();
	public void update(T object);
	public void delete(Long id);
	public void delete(List<Long> list);
}
