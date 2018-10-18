package com.excilys.cdb2.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

//import org.apache.log4j.Logger;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.persistence.CompanyDao;
import com.excilys.cdb2.service.CompanyServices;
import com.excilys.cdb2.service.ComputerServices;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private final static Logger LOGGER = Logger.getLogger(AddComputer.class);
	
	@Autowired
	private ComputerServices computerServices;
	
	@Autowired
	private CompanyServices companyServices;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditComputer() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
	super.init(config);
	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Company> companies;
		try {
			companies = companyServices.showCompanies();
			request.setAttribute("companies", companies);
		} catch (ValidationException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String id = request.getParameter("id");
		try {
			Computer computer = computerServices.showComputerDetail(id);

			try {
				if (computer.getCompanyName().isPresent() ) {
					long idCompany = companyServices.showCompanyId(computer.getCompanyName().orElse("0"));
					request.setAttribute("idCompany", idCompany);
				}
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("computer", computer);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/views/EditComputer.jsp" ).include( request, response );
		}
		catch(ValidationException | ClassNotFoundException e) {
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
		String id = request.getParameter("id");
		String name = request.getParameter("computerName");  
		String introduced = request.getParameter("introduced");  
		String discontinued = request.getParameter("discontinued");
		String company = request.getParameter("companyId");
		try {
			if(!introduced.equals("") && !discontinued.equals("")) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date1 = sdf.parse(introduced);
				java.util.Date date2 = sdf.parse(discontinued);

				if(date1.compareTo(date2) > 0) {
					String dateError="ko";
					request.setAttribute("dateError", dateError);
					//LOGGER.error("La date de début est supérieure à la date de fin.");
					throw new ValidationException("La date de début est supérieure à la date de fin.");


				}
			}
			computerServices.modifyComputer(id, name, introduced, discontinued, company);
			request.setAttribute("messageOk", messageOk);
		}
		catch(ValidationException | ParseException | ClassNotFoundException e) {
			//request.setAttribute("messageKo", messageKo);
			//LOGGER.error("Les valeurs que vous avez entrées ne sont pas correctes, veuillez recommencer.");
		}
		doGet(request, response);
		//getServletContext().getRequestDispatcher("/EditComputer").forward(request, response);
		//response.sendRedirect("Dashboard?messageError="+messageError);
	}

}
