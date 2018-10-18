package com.excilys.cdb2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.service.ComputerServices;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/DeleteComputer")
public class DeleteComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ComputerServices computerServices;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page;
        int limit;
        try {
        	page = Integer.parseInt(request.getParameter("page"));
        	limit = Integer.parseInt(request.getParameter("limit"));
        } catch (NumberFormatException e) {
        	page = 1;
        	limit = 10;

        }
        response.sendRedirect(new StringBuilder("/cdb2/Dashboard?page=").append(page).append("&limit=")
                .append(limit).toString());
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String selection = request.getParameter("selection");
		List<Long> ids = new ArrayList<>();
        for (String idString : selection.split(",")) {
            ids.add(Long.parseLong(idString));
        }
        try {
        	computerServices.deleteComputer(ids);
			doGet(request, response);
		} catch (ValidationException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
