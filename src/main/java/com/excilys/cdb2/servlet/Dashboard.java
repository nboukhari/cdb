package com.excilys.cdb2.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.service.ComputerServices;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		int nbComp = 0;
		String search = request.getParameter("search");
		
		try {
			if(search ==null) {
			nbComp = ComputerServices.getNumberComputers();
			}
			else {
				nbComp = ComputerServices.getNumberComputersFromSearch(search);
			}
			request.setAttribute("nbComp", nbComp);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error ",e);
			System.out.println("error "+e);
		}


		String limit;
		limit = request.getParameter("limit");
		
		if (limit ==null) {
			limit ="10";
		}
		if(limit.equals("10") || limit.equals("50") || limit.equals("100")){

			int nbPages = (nbComp / Integer.parseInt(limit))+1;			
			String numberOfPage = request.getParameter("page");
			
			if (numberOfPage ==null) {
				numberOfPage ="1";
			}
			
			int nbPage = Integer.parseInt(numberOfPage);

			if(nbPage < 1 || nbPage > nbPages) {
				getServletContext().getRequestDispatcher( "/WEB-INF/views/404.html" ).include( request, response );
			}
			else {
				int nbPageMinusOne = nbPage - 1;
				int nbPageMinusTwo = nbPage - 2;
				int nbPageMoreOne = nbPage + 1;
				int nbPageMoreTwo = nbPage + 2;

				List<Computer> computers;
				try {
					if(search ==null) {
					computers = ComputerServices.showComputers(numberOfPage,limit);
					}
					else {
						computers = ComputerServices.showComputersFromSearch(search, numberOfPage,limit);
					}
					request.setAttribute("computers", computers);
					request.setAttribute("limit", limit);
					request.setAttribute("nbPage", nbPage);
					request.setAttribute("nbPages", nbPages);
					request.setAttribute("nbPageMinusOne", nbPageMinusOne);
					request.setAttribute("nbPageMinusTwo", nbPageMinusTwo);
					request.setAttribute("nbPageMoreOne", nbPageMoreOne);
					request.setAttribute("nbPageMoreTwo", nbPageMoreTwo);
					this.getServletContext().getRequestDispatcher( "/WEB-INF/views/Dashboard.jsp" ).forward( request, response );	
				} 

				catch (ValidationException e) {
					LOGGER.error("Error ",e);
				}
			}
		}
		else {
			getServletContext().getRequestDispatcher( "/WEB-INF/views/404.html" ).include( request, response );
		}	
	}
}
