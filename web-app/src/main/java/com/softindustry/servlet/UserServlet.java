package com.softindustry.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softindustry.entity.User;
import com.softindustry.service.UserService;
import com.softindustry.util.UserValidator;

@WebServlet(name = "GuestBookServlet", urlPatterns = {"/", "/search", "/makeuser", "/edit"})
public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UserService service;
	private UserValidator validator;
	
	public UserServlet() {
		super();
		service = new UserService();
		validator = new UserValidator();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String userId = req.getParameter("id");
		
		if(userId != null && !userId.isEmpty()) {
			try {
				req.setAttribute("user", service.getUserById(userId));
			} catch(Exception e) {
				req.setAttribute("error", e.getMessage() + e.getCause().getMessage() + e.getCause().getCause().getMessage());
			}
			getServletContext().getRequestDispatcher("/jsp/userdetails.jsp").forward(req, res);
		} else {
			doPut(req, res);
		}
	}
	
	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<User> users = null;
		String searchReq = req.getParameter("search_req");
		
		if(searchReq == null || searchReq.isEmpty()) {
			users = service.getAllUsers();
		} else {
			users = service.getUsersByParam(searchReq, req.getParameter("search_param"));
		}
		req.setAttribute("users", users);
		getServletContext().getRequestDispatcher("/jsp/users.jsp").forward(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		if(!validator.validateUsersData(req)) {
			if(req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
				User user = createUserFromReqParams(req);
				user.setId(Long.valueOf(req.getParameter("id")));
				req.setAttribute("user", user);
			}
			getServletContext().getRequestDispatcher("/jsp/userdetails.jsp").forward(req, res);
		}
		
		User user = createUserFromReqParams(req);
		
		req.setCharacterEncoding("UTF-8");
		
		if(req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
			user.setId(Long.valueOf(req.getParameter("id")));
			req.setAttribute("user", user);
		}
		try {
			service.saveUser(user);
			req.setAttribute("success", "Сохранение прошло успешно");
		} catch(Exception e) {
			req.setAttribute("error", "Сохранение не удалось");
		}
		getServletContext().getRequestDispatcher("/jsp/userdetails.jsp").forward(req, res);
	}
	
	private User createUserFromReqParams(HttpServletRequest req) {
		User user = new User();
		user.setLastName(req.getParameter("lastName"));
		user.setFirstName(req.getParameter("firstName"));
		user.setAge(Short.valueOf(req.getParameter("age")));
		user.setGender(req.getParameter("gender").charAt(0));
		user.setPhone(req.getParameter("phone"));
		return user;
	}
}
