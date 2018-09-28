package com.excilys.cdb2.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
 * Servlet implementation class EditComputer
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
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
		String id = request.getParameter("id");
		Computer computer = ComputerDao.getComputerDetails(id);
	        try {
	        	if (computer.getCompanyName().isPresent() ) {
				long idCompany = CompanyDao.getCompanyId(computer.getCompanyName().orElse("0"));
				request.setAttribute("idCompany", idCompany);
	        	}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		request.setAttribute("computer", computer);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/EditComputer.jsp" ).include( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String Aid = request.getParameter("id");
		String Aname = request.getParameter("computerName");  
        String Aintroduced = request.getParameter("introduced");  
        String Adiscontinued = request.getParameter("discontinued");
        String Acompany = request.getParameter("companyId");
        Computer computer =  ComputerDao.updateComputer(Aid, Aname, Aintroduced, Adiscontinued, Acompany);
        response.sendRedirect("Dashboard");
	}

}
