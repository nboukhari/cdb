package com.excilys.cdb2.servlet;

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
		String messageCreate="ok";
		String name = request.getParameter("computerName");  
        String introduced = request.getParameter("introduced");  
        String discontinued = request.getParameter("discontinued");
        String company = request.getParameter("companyId");
        if (company.equals("0")) {
        	company = null;
        }
        try {
			ComputerDao.setComputer(name, introduced, discontinued, company);
			messageCreate.equals("ok");
			request.setAttribute("messageCreate", messageCreate);
			System.out.println("done");
			request.getRequestDispatcher("Dashboard").forward(request, response);
			//request.getRequestDispatcher("/WEB-INF/views/Dashboard.jsp").include(request, response);
			//response.sendRedirect("AddComputer");
			System.out.println("done after");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("RIP");
			e.printStackTrace();
		}
	}

}