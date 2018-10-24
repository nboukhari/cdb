package com.excilys.cdb2.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.service.CompanyServices;
import com.excilys.cdb2.service.ComputerServices;

@Controller
@RequestMapping("/EditComputer")
public class EditComputerController {

	@Autowired
	private ComputerServices computerServices;
	
	@Autowired
	private CompanyServices companyServices;
	
	@GetMapping
	public String handleGet(@RequestParam("id") String id, Model model) throws ClassNotFoundException, IOException, ValidationException, SQLException {
		
		List<Company> companies;
		companies = companyServices.showCompanies();
		model.addAttribute("companies", companies);
		
		try {
			Computer computer = computerServices.showComputerDetail(id);
				if (computer.getCompanyName().isPresent() ) {
					long idCompany = companyServices.showCompanyId(computer.getCompanyName().orElse("0"));
					model.addAttribute("idCompany", idCompany);
				}
			
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
			if(!introduced.equals("") && !discontinued.equals("")) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date1 = sdf.parse(introduced);
				java.util.Date date2 = sdf.parse(discontinued);

				if(date1.compareTo(date2) > 0) {
					String dateError="ko";
					model.addAttribute("dateError", dateError);
					//LOGGER.error("La date de début est supérieure à la date de fin.");
					throw new ValidationException("La date de début est supérieure à la date de fin.");


				}
			}
			computerServices.modifyComputer(id, name, introduced, discontinued, company);
			model.addAttribute("messageOk", messageOk);
			//request.setAttribute("messageOk", messageOk);
		}
		catch(ValidationException | ParseException | ClassNotFoundException e) {
			//request.setAttribute("messageKo", messageKo);
			//LOGGER.error("Les valeurs que vous avez entrées ne sont pas correctes, veuillez recommencer.");
		}
	
		return handleGet(id, model);
	}
}
