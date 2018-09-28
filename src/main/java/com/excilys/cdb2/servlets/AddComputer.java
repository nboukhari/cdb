package com.excilys.cdb2.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.persistence.CompanyDao;
import com.excilys.cdb2.persistence.ComputerDao;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Company> companies = CompanyDao.getAllCompanies();
		request.setAttribute("companies", companies);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/AddComputer.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String Aname = request.getParameter("computerName");  
        String Aintroduced = request.getParameter("introduced");  
        String Adiscontinued = request.getParameter("discontinued");
        String Acompany = request.getParameter("companyId");
        if (Acompany.equals("0")) {
        	Acompany = null;
        }
        try {
			ComputerDao.setComputer(Aname, Aintroduced, Adiscontinued, Acompany);
			response.sendRedirect("Dashboard");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("RIP");
			e.printStackTrace();
			PrintWriter test = response.getWriter();
			test.println("<h1>WTFFFFFFFF</h1>");
		}
	}

}