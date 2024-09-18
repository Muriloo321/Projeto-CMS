package services;

import java.util.List;

import models.dao.DaoFactory;
import models.dao.UserDao;
import models.entities.User;

public class UserService {
	UserDao userDao = DaoFactory.createUserDao();

	public boolean login(User user) {
		return validateUser(user);
	}

	private boolean validateUser(User user) {
		User foundUser = userDao.findByUser(user);
		return foundUser != null && foundUser.getPassword().equals(user.getPassword());
	}

	public boolean register(User user) {
		if (userDao.findByName(user.getName()) == null) {
			userDao.insert(user);
			return true;
		}
		return false;
	}

	public User findByUser(User user) {
		return userDao.findByUser(user);
	}
	
	public User findByName(String name) {
	    User user = userDao.findByName(name);
	    if (user != null) {
	        return user;
	    } else {
	        throw new RuntimeException("Usuário não encontrado.");
	    }
	}


	public User findById(int id) {
		User user = userDao.findById(id);
		if (user != null) {
			return user;
		} else {
			throw new RuntimeException("Usuário não encontrado.");
		}
	}

	public String update(User user) {
		User existingUser = userDao.findById(user.getId());
		if (existingUser != null) {
			userDao.update(user);
			return "Usuário atualizado com sucesso!";
		} else {
			return "Usuário não encontrado.";
		}
	}

	public String deleteById(int id) {
		Boolean removed = userDao.deleteById(id);
		if (removed) {
			return "Usuário excluído com sucesso!";
		} else {
			return "Usuário não encontrado.";
		}
	}

	public List<User> findAll() {
		return userDao.findAll();
	}
}
