package com.softindustry.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class UserValidator {
	
	public boolean validateUsersData(HttpServletRequest req) {
		boolean isValid = true;
		
		String lastName = req.getParameter("lastName");
		String firstName = req.getParameter("firstName");
		String age = req.getParameter("age");
		String gender = req.getParameter("gender");
		String phone = req.getParameter("phone");
        
        if(!isValid(lastName, "^[а-яА-Я|a-zA-Z]{5,20}$")) {
        	isValid = false;
        	req.setAttribute("invalidLastName", "Фамилия должна состоять не менее "
        			+ "чем из 5 и не более чем из 20 букв кирилицы, либо латиницы");
        }
        if(!isValid(firstName, "^[а-яА-Я|a-zA-Z]{5,20}$")) {
        	isValid = false;
        	req.setAttribute("invalidFirstName", "Имя должно состоять не менее "
        			+ "чем из 5 и не более чем из 20 букв кирилицы, либо латиницы");
        }
        if(!isValid(age, "^\\d{1,3}$")) {
        	isValid = false;
        	req.setAttribute("invalidAge", "Введите только число от 0 до 200");
        }
        if(!isValid(gender, "^[м|ж]$")) {
        	isValid = false;
        	req.setAttribute("invalidGender", "Введите либо 'м', либо 'ж'");
        }
        if(!isValid(phone, "^\\+\\d{2,2}\\(\\d{3,3}\\)\\d{7,7}$")) {
        	isValid = false;
        	req.setAttribute("invalidPhone", "Некорректный формат номера: +хх(ххх)ххххххх");
        }
        return isValid;
	}
	
	private boolean isValid(String str, String pattern) {
		return Pattern.compile(pattern).matcher(str).matches();
	}
}
