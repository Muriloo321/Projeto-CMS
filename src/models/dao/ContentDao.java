package models.dao;

import java.util.List;

import models.entities.Content;

public interface ContentDao {

	void insert(Content content);
	void update(Content content);
	Boolean deleteById(int id);
	Content findById(int id);
	List<Content> findAll();
}
