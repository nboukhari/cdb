package com.excilys.cdb2.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.service.ComputerServices;

@Controller
@RequestMapping("/Dashboard")
public class DashboardController {

	@Autowired
	private ComputerServices computerServices;
	private static final String DEFAULT_PAGE = "1";
	private static final String DEFAULT_SIZE = "10";
	private static final String DEFAULT_SEARCH = "";

	@GetMapping
	public String handleGet(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) String pageNumber,
			@RequestParam(value = "limit", defaultValue = DEFAULT_SIZE) String pageSize,
			@RequestParam(value = "search", defaultValue = DEFAULT_SEARCH) String search, Model model) throws ClassNotFoundException, IOException, ValidationException {

		int nbComp = 0;
		if(search.equals(null)) {
			nbComp = computerServices.getNumberComputers();
			}
			else {
				nbComp = computerServices.getNumberComputersFromSearch(search);
			}
		if(pageSize.equals("10") || pageSize.equals("50") || pageSize.equals("100")){

			int nbPages = (nbComp / Integer.parseInt(pageSize))+1;
			
			int nbPage = Integer.parseInt(pageNumber);

			if(nbPage < 1 || nbPage > nbPages) {
				return "/404";
				//getServletContext().getRequestDispatcher( "/WEB-INF/views/404.html" ).include( request, response );
			}
			else {
				int nbPageMinusOne = nbPage - 1;
				int nbPageMinusTwo = nbPage - 2;
				int nbPageMoreOne = nbPage + 1;
				int nbPageMoreTwo = nbPage + 2;

				List<Computer> computers;
				
					if(search.equals(null)) {
					computers = computerServices.showComputers(pageNumber,pageSize);
					}
					else {
						computers = computerServices.showComputersFromSearch(search, pageNumber,pageSize);
					}
					model.addAttribute("nbComp",nbComp);
					model.addAttribute("search",search);
					model.addAttribute("computers", computers);
					model.addAttribute("pageSize", pageSize);
					model.addAttribute("nbPage", nbPage);
					model.addAttribute("nbPages", nbPages);
					model.addAttribute("nbPageMinusOne", nbPageMinusOne);
					model.addAttribute("nbPageMinusTwo", nbPageMinusTwo);
					model.addAttribute("nbPageMoreOne", nbPageMoreOne);
					model.addAttribute("nbPageMoreTwo", nbPageMoreTwo);
				} 

			}
		
		else {
			System.out.println("oh");
			return "/404";
			//getServletContext().getRequestDispatcher( "/WEB-INF/views/404.html" ).include( request, response );
		}	
		return "Dashboard" ;
	}
}