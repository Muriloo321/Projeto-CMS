package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				String user = props.getProperty("user");
				String password = props.getProperty("password");
				conn = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void createTables() {
		Connection conn = getConnection();
		try (Statement stmt = conn.createStatement()) {
			stmt.execute("DROP TABLE IF EXISTS Content");
			stmt.execute("DROP TABLE IF EXISTS User");

			stmt.execute("CREATE TABLE Content (" + "Id INTEGER IDENTITY PRIMARY KEY, " + "Title VARCHAR(255), "
					+ "Text VARCHAR(255), " + "Author VARCHAR(255)" + ")");

			stmt.execute("CREATE TABLE User (" + "Id INTEGER IDENTITY PRIMARY KEY, " + "Name VARCHAR(255), "
					+ "Password VARCHAR(255)" + ")");
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

}
