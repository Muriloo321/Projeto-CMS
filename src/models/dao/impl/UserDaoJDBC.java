package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import models.dao.UserDao;
import models.entities.User;

public class UserDaoJDBC implements UserDao {

	private Connection conn = null;

	public UserDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(User user) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("INSERT INTO User (Name, Password) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					user.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public User findByUser(User user) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM User WHERE Name = ? AND Password = ?");

			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());

			rs = ps.executeQuery();

			if (rs.next()) {
				User foundUser = instantiateUser(rs);
				return foundUser;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public User findByName(String name) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM User WHERE Name = ?");

			ps.setString(1, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				User foundUser = instantiateUser(rs);
				return foundUser;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void update(User user) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("UPDATE User SET Name = ?, Password = ? WHERE Id = ?");

			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getId());

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected == 0) {
				throw new DbException("Update failed! No rows affected.");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public User findById(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM User WHERE Id = ?");

			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				User foundUser = instantiateUser(rs);
				return foundUser;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Boolean deleteById(int id) {
		PreparedStatement ps = null;
		Boolean isDeleted = false;

		try {
			ps = conn.prepareStatement("DELETE FROM User WHERE Id = ?");

			ps.setInt(1, id);

			int rowsAffected = ps.executeUpdate();
			isDeleted = rowsAffected > 0;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}

		return isDeleted;
	}

	@Override
	public List<User> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM User ORDER BY Name");
			rs = st.executeQuery();

			List<User> list = new ArrayList<>();

			while (rs.next()) {
				User obj = instantiateUser(rs);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private User instantiateUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("Id"));
		user.setName(rs.getString("Name"));
		user.setPassword(rs.getString("Password"));
		return user;
	}
}
