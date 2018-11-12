package com.excilys.cdb2.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.service.CompanyServices;
import com.excilys.cdb2.service.ComputerServices;
import com.excilys.cdb2.validator.isValidFormat;

@Controller
@RequestMapping("/EditComputer")
public class EditComputerController {
	@Autowired
	private ComputerServices computerServices;
	@Autowired
	private CompanyServices companyServices;
	
	private final static Logger logger = LoggerFactory.getLogger("EditComputerController");
	
	@GetMapping
	public String handleGet(@RequestParam("id") String id, Model model) throws ClassNotFoundException, IOException, ValidationException, SQLException {
		
		List<Company> companies;
		companies = companyServices.showCompanies();
		model.addAttribute("companies", companies);
		
		try {
			Computer computer = computerServices.showComputerDetail(id);
				/*if (computer.getCompanyName().isPresent() ) {
					long idCompany = companyServices.showCompanyId(computer.getCompanyName().orElse("0"));
					model.addAttribute("idCompany", idCompany);
				}*/
			model.addAttribute("id", id);
			model.addAttribute("computer", computer);
		}
		catch(ValidationException | ClassNotFoundException e) {
			return "404";
		}
		return "EditComputer";
	}
	
	@PostMapping
	public String handlePost(@RequestParam("id") String id,
			@RequestParam("computerName") String name,
			@RequestParam("introduced") String introduced,
			@RequestParam("discontinued") String discontinued,
			@RequestParam("companyId") String company, Model model) throws IOException, ClassNotFoundException, ValidationException, SQLException {
		String messageOk="ok";
		try {
			if(isValidFormat.dateSuperior(introduced, discontinued).equals("ko")) {
				String dateError ="ko";
				model.addAttribute("dateError",dateError);
			}
			else {
			computerServices.modifyComputer(id, name, introduced, discontinued, company);
			model.addAttribute("messageOk", messageOk);
			}
		}
		catch(ValidationException | ParseException | ClassNotFoundException e) {
			logger.error("Les valeurs que vous avez entrées ne sont pas correctes, veuillez recommencer.");
		}
	
		return handleGet(id, model);
	}
}
