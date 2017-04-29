package com.softindustry.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

import com.softindustry.entity.User;

public class UserDao {

	private static final String DB_PROPERTIES_FILE = "DB.properties";
	private static final String SAVE_USER_QUERY =
			"INSERT INTO `users`(last_name, first_name, age, gender, phone)"
			+ " VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_USER_QUERY =
			"UPDATE `users` SET last_name = ?, first_name = ?, age = ?, gender = ?, phone = ?"
			+ " WHERE id = ?";
	private static final String GET_ALL_QUERY = "SELECT * FROM `users`";
	private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM `users` WHERE id = ?";
	private static final String GET_USERS_BY_PARAM_QUERY = "SELECT * FROM `users` WHERE ";
	
	private static BasicDataSource dataSource;
	
	public UserDao() {
		initDataSource();
	}
	
	public void saveUser(User user, boolean isUpdate) {
		try (Connection conn = getConnection()) {
			String query = isUpdate ? UPDATE_USER_QUERY : SAVE_USER_QUERY;
			
			try (PreparedStatement st = conn.prepareStatement(query)) {
				st.setString(1, user.getLastName());
				st.setString(2, user.getFirstName());
				st.setShort(3, user.getAge());
				st.setString(4, user.getGender().toString());
				st.setString(5, user.getPhone());
				
				if(isUpdate) {
					st.setLong(6, user.getId());
				}
				st.execute();
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw new RuntimeException("Fail to save a user", e);
			}
		} catch (SQLException e1) {
			throw new RuntimeException("A problem with connection", e1);
		}
	}
	
	public List<User> getUsersByParam(String pattern, String field, boolean isTextField) {
		try (Connection conn = getConnection()) {
			String query = GET_USERS_BY_PARAM_QUERY + field
					+ (isTextField ? " LIKE ?" : " = ?");
			
			try (PreparedStatement st = conn.prepareStatement(query)) {
				pattern = isTextField ? "%" + pattern + "%" : pattern;
				
				st.setString(1, pattern);
				
				return getUsersFromResultSet(st.executeQuery());
				
			} catch (SQLException e) {
				throw new RuntimeException("Fail to get users", e);
			}
		} catch (SQLException e1) {
			throw new RuntimeException("A problem with connection", e1);
		}
	}
	
	public List<User> getAllUsers() {
		try (Connection conn = getConnection()) {
			try (PreparedStatement st = conn.prepareStatement(GET_ALL_QUERY)) {
				
				return getUsersFromResultSet(st.executeQuery());
			} catch (SQLException e) {
				throw new RuntimeException("Fail to get users", e);
			}
		} catch (SQLException e1) {
			throw new RuntimeException("A problem with connection", e1);
		}
	}
	
	public User getUserById(Long id) {
		try (Connection conn = getConnection()) {
			try (PreparedStatement st = conn.prepareStatement(GET_USER_BY_ID_QUERY)) {
				
				st.setLong(1, id);
				return getUsersFromResultSet(st.executeQuery()).get(0);
				
			} catch (SQLException e) {
				throw new RuntimeException("Fail to get user", e);
			}
		} catch (SQLException e1) {
			throw new RuntimeException("A problem with connection", e1);
		}
	}
	
	private void initDataSource() {
		if (dataSource != null) {
			return;
		}
		try (InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE)) {
			Properties properties = new Properties();
			properties.load(inputStream);
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(properties.getProperty("driver"));
			dataSource.setUrl(properties.getProperty("url"));
			dataSource.setUsername(properties.getProperty("username"));
			dataSource.setPassword(properties.getProperty("password"));
		} catch (IOException e) {
			throw new RuntimeException("Couldn't get DB properties", e);
		}
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = dataSource.getConnection();
		conn.setAutoCommit(false);
		return conn;
	}
	
	private List<User> getUsersFromResultSet(ResultSet res) throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		while(res.next()) {
			User user = new User();
			user.setId(res.getLong("id"));
			user.setLastName(res.getString("last_name"));
			user.setFirstName(res.getString("first_name"));
			user.setAge(res.getShort("age"));
			user.setGender(res.getString("gender").charAt(0));
			user.setPhone(res.getString("phone"));
			list.add(user);
		}
		return list;
	}
}
