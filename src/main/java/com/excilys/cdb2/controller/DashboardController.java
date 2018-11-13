package com.excilys.cdb2.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.Pagination;
import com.excilys.cdb2.service.ComputerServices;

@Controller
@RequestMapping("/Dashboard")
public class DashboardController {

	@Autowired
	private Pagination pagination;

	@Autowired
	private ComputerServices computerServices;

	private static final String DEFAULT_PAGE = "1";
	private static final String DEFAULT_SIZE = "10";

	@GetMapping
	public String handleGet(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE) String pageNumber,
			@RequestParam(name = "limit", defaultValue = DEFAULT_SIZE) String pageSize,
			@RequestParam(required=false, name ="search") String search, Model model) throws ClassNotFoundException, IOException, ValidationException {
		int nbComputers = 0;
		if(search == null || "".equals(search) ) {
			nbComputers = computerServices.getNumberComputers();
		}
		else {
			nbComputers = computerServices.getNumberComputersFromSearch(search);
		}
		if(pageSize.equals("10") || pageSize.equals("50") || pageSize.equals("100")){

			int nbPages = (nbComputers / Integer.parseInt(pageSize))+1;
			int numPage = Integer.parseInt(pageNumber);
			
			if(numPage < 1 || numPage > nbPages) {
				return "/404";
			}
			else {
				List<Computer> computers;

				if("".equals(search) || search == null) {
					computers = computerServices.showComputers(pageNumber,pageSize);
				}
				else {
					computers = computerServices.showComputersFromSearch(search, pageNumber,pageSize);
				}
				model.addAttribute("nbComp",nbComputers)
				.addAttribute("search",search)
				.addAttribute("computers", computers)
				.addAttribute("pageSize", pageSize)
				.addAttribute("nbPage", numPage)
				.addAttribute("nbPages", nbPages)
				.addAttribute("nbPageMinusOne", pagination.numPageMinusOne(numPage))
				.addAttribute("nbPageMinusTwo", pagination.numPageMinusTwo(numPage))
				.addAttribute("nbPageMoreOne", pagination.numPagePlusOne(numPage))
				.addAttribute("nbPageMoreTwo",  pagination.numPagePlusTwo(numPage));
			} 

		}
		else {
			return "/404";
		}	
		return "Dashboard" ;
	}
}
