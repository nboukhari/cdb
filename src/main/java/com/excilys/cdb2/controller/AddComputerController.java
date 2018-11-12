package com.excilys.cdb2.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.service.CompanyServices;
import com.excilys.cdb2.service.ComputerServices;
import com.excilys.cdb2.validator.isValidFormat;

@Controller
@RequestMapping("/AddComputer")
public class AddComputerController {

	@Autowired
	private ComputerServices computerServices;
	@Autowired
	private CompanyServices companyServices;

	@GetMapping
	public String handleGet(Model model) throws ClassNotFoundException, IOException, ValidationException, SQLException {

		List<Company> companies;
		companies = companyServices.showCompanies();
		model.addAttribute("companies", companies);

		return "AddComputer";
	}

	@PostMapping
	public String handlePost(@RequestParam("computerName") String name, @RequestParam("introduced") String introduced,
			@RequestParam("discontinued") String discontinued, @RequestParam("companyId") String company,
			@ModelAttribute("messageCreate") String messageCreate, final RedirectAttributes redirectAttributes,
			Model model) throws IOException, ClassNotFoundException, ValidationException, SQLException, ParseException {
		messageCreate = "ok";
		if (company.equals("0")) {
			company = null;
		}
		if (isValidFormat.dateSuperior(introduced, discontinued).equals("ko")) {
			String dateError = "ko";
			model.addAttribute("dateError", dateError);
			return "AddComputer";
		} else {
			computerServices.createComputer(name, introduced, discontinued, company);
			redirectAttributes.addFlashAttribute("messageCreate", messageCreate);
			return "redirect:Dashboard";
		}
	}

}
