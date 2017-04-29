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
        
        if(!isValid(lastName, "^[�-��-�|a-zA-Z]{5,20}$")) {
        	isValid = false;
        	req.setAttribute("invalidLastName", "������� ������ �������� �� ����� "
        			+ "��� �� 5 � �� ����� ��� �� 20 ���� ��������, ���� ��������");
        }
        if(!isValid(firstName, "^[�-��-�|a-zA-Z]{5,20}$")) {
        	isValid = false;
        	req.setAttribute("invalidFirstName", "��� ������ �������� �� ����� "
        			+ "��� �� 5 � �� ����� ��� �� 20 ���� ��������, ���� ��������");
        }
        if(!isValid(age, "^\\d{1,3}$")) {
        	isValid = false;
        	req.setAttribute("invalidAge", "������� ������ ����� �� 0 �� 200");
        }
        if(!isValid(gender, "^[�|�]$")) {
        	isValid = false;
        	req.setAttribute("invalidGender", "������� ���� '�', ���� '�'");
        }
        if(!isValid(phone, "^\\+\\d{2,2}\\(\\d{3,3}\\)\\d{7,7}$")) {
        	isValid = false;
        	req.setAttribute("invalidPhone", "������������ ������ ������: +��(���)�������");
        }
        return isValid;
	}
	
	private boolean isValid(String str, String pattern) {
		return Pattern.compile(pattern).matcher(str).matches();
	}
}
