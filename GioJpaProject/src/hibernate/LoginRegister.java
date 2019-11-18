package hibernate;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hibernate.Customer;
import hibernate.CustomerDAO;
import hibernate.CustomerDAOImpl;

/**
 * Servlet implementation class LoginRegister
 */
@WebServlet("/LoginRegister")
public class LoginRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginRegister() {
       
    }

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CustomerDAO cd = new CustomerDAOImpl();
		String userName = request.getParameter("username");
		String password = request.getParameter("password1");
		String submitType = request.getParameter("submit");
		
		HttpSession session = request.getSession(true);
		
		Customer c = new Customer();
		c = cd.getCustomer(userName,  password);
		boolean flag = cd.check(userName, password);
		
		if(submitType.equals("login") && c != null) {
			request.setAttribute("message", userName);
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}else if(submitType.equals("Register") && c == null && flag == false) {
			c= new Customer();
			c.setName(request.getParameter("name"));
			c.setPassword(password);
			c.setUsername(userName);
			cd.insertCustomer(c);
			request.setAttribute("successMessage", "Registration done. Login to continue !!!");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			
		}else {
			if(submitType.equals("Register")) {
				request.setAttribute("message", "Username is already used. Choose another username !!!");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}else {
				request.setAttribute("message", "Data not Found, click on Register !!!");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
	}

}


