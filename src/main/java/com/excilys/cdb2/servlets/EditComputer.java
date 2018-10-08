package com.excilys.cdb2.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
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
		try {
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
		catch(NullPointerException e) {
			getServletContext().getRequestDispatcher( "/WEB-INF/views/404.html" ).include( request, response );
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String messageOk="ok";
		String messageKo="ko";
		String id = request.getParameter("id");
		String name = request.getParameter("computerName");  
        String introduced = request.getParameter("introduced");  
        String discontinued = request.getParameter("discontinued");
        String company = request.getParameter("companyId");
        try {
        Computer computer =  ComputerDao.updateComputer(id, name, introduced, discontinued, company);
        request.setAttribute("messageOk", messageOk);
        }
        catch(Exception e) {
        	request.setAttribute("messageKo", messageKo);
        }
        doGet(request, response);
		//getServletContext().getRequestDispatcher("/EditComputer").forward(request, response);
        //response.sendRedirect("Dashboard?messageError="+messageError);
	}

}
