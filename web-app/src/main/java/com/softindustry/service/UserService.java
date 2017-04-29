package com.softindustry.service;

import java.util.List;

import com.softindustry.dao.UserDao;
import com.softindustry.entity.User;

public class UserService {
	
	private UserDao dao;
	
	public UserService() {
		dao = new UserDao();
	}
	
	public List<User> getAllUsers() {
		return dao.getAllUsers();
	}
	
	public List<User> getUsersByParam(String query, String param) {
		return dao.getUsersByParam(query, param, isParamText(param));
	}
	
	public User getUserById(String id) {
		try {
			return dao.getUserById(Long.valueOf(id));
		} catch (Exception e) {
			throw new RuntimeException("Пользователя с таким номером нет", e);
		}
	}
	
	public void saveUser(User user) {
		dao.saveUser(user, user.getId() != null);
	}
	
	private boolean isParamText(String param) {
		return param.equalsIgnoreCase("first_name")
				|| param.equalsIgnoreCase("last_name")
				|| param.equalsIgnoreCase("gender")
				|| param.equalsIgnoreCase("phone");
	}
}
