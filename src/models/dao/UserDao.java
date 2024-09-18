package models.dao;

import java.util.List;

import models.entities.User;

public interface UserDao {

	void insert(User User);
	void update(User user);
	User findByUser(User user);
	User findByName(String name);
	User findById(int id);
	Boolean deleteById(int id);
	List<User> findAll();
}