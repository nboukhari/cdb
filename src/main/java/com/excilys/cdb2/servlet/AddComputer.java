package com.excilys.cdb2.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb2.exception.ValidationException;
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
	private final static Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);
	
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
		List<Company> companies;
		try {
			companies = CompanyDao.getAllCompanies();
			request.setAttribute("companies", companies);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/views/AddComputer.jsp" ).forward( request, response );
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
        	if(!introduced.equals("") && !discontinued.equals("")) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date1 = sdf.parse(introduced);
				java.util.Date date2 = sdf.parse(discontinued);

				if(date1.compareTo(date2) > 0) {
					String dateError="ko";
					request.setAttribute("dateError", dateError);
					this.getServletContext().getRequestDispatcher( "/WEB-INF/views/AddComputer.jsp" ).forward( request, response );
					LOGGER.error("La date de début est supérieure à la date de fin.");
					throw new ValidationException("La date de début est supérieure à la date de fin.");
					
				
				}
			}
			ComputerDao.setComputer(name, introduced, discontinued, company);
			request.setAttribute("messageCreate", messageCreate);
		    response.sendRedirect(new StringBuilder("/cdb2/Dashboard?limit=10&page=1").toString());
		} catch (ValidationException | ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error", e);
		}
	}

}