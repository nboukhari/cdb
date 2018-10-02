package com.excilys.cdb2.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.service.ComputerServices;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		try {
			nbComp = ComputerServices.getNumberComputers();
			request.setAttribute("nbComp", nbComp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		String limit = "10";
		limit = request.getParameter("limit");
		int nbPages = (nbComp / ComputerMapper.stringToInt(limit))+1;
		request.setAttribute("nbPages", nbPages);
		System.out.println("NOMBRE DE PAGES: "+nbPages);
		String NumberOfPage = request.getParameter("page");
		System.out.println("WAYE "+NumberOfPage);
		List<Computer> computers = ComputerServices.showComputers(NumberOfPage,limit);
		request.setAttribute("computers", computers);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/Dashboard.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
