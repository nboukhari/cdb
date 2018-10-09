package com.excilys.cdb2.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb2.persistence.ComputerDao;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/DeleteComputer")
public class DeleteComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
        // TODO Auto-generated constructor stub
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
        ComputerDao.removeComputer(ids);
		doGet(request, response);
	}

}
