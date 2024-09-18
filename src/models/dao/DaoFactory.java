package models.dao;

import db.DB;
import models.dao.impl.ContentDaoJDBC;
import models.dao.impl.UserDaoJDBC;

public class DaoFactory {

	public static UserDao createUserDao() {
		return new UserDaoJDBC(DB.getConnection());
	}
	
	public static ContentDao createContentDao() {
		return new ContentDaoJDBC(DB.getConnection());
	}
}