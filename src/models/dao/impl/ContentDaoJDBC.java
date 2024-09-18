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
import models.dao.ContentDao;
import models.entities.Content;
import models.entities.User;

public class ContentDaoJDBC implements ContentDao {

	private Connection conn = null;

	public ContentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Content content) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("INSERT INTO Content (Title, Text, Author) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, content.getTitle());
			ps.setString(2, content.getText());
			ps.setString(3, content.getAuthor().getName());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					content.setId(id);
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
	public void update(Content content) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(
					"UPDATE Content " + "SET Title = ?, Text = ? " + "WHERE Id = ? ");

			ps.setString(1, content.getTitle());
			ps.setString(2, content.getText());
			ps.setInt(3, content.getId());
			
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public Boolean deleteById(int id) {
		PreparedStatement ps = null;
		Boolean isDeleted = false;

		try {
			ps = conn.prepareStatement("DELETE FROM Content WHERE Id = ?");

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
	public Content findById(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT Content.* " + "FROM Content " + "WHERE Content.Id = ? ");

			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				Content Content = instantiateContent(rs);
				return Content;
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
	public List<Content> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM Content ORDER BY Title");
			rs = st.executeQuery();

			List<Content> list = new ArrayList<>();

			while (rs.next()) {
				Content obj = instantiateContent(rs);
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

	private Content instantiateContent(ResultSet rs) throws SQLException {
		Content content = new Content();
		content.setId(rs.getInt("Id"));
		content.setTitle(rs.getString("Title"));
		content.setText(rs.getString("Text"));
		
		String authorName = rs.getString("Author");  
	    User author = new User();  
	    author.setName(authorName);
	    content.setAuthor(author);
	    
		return content;
	}

}
