package com.excilys.cdb2.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.service.ComputerServices;

@Controller
@RequestMapping("/DeleteComputer")
public class DeleteController {
	@Autowired
	private ComputerServices computerServices;

	@GetMapping
	public String handleGet() throws ClassNotFoundException, IOException, ValidationException, SQLException {
		return "redirect:Dashboard";
	}

	@PostMapping
	public String handlePost(@RequestParam("selection") String selection, Model model) throws IOException, ClassNotFoundException, ValidationException, SQLException, ParseException {
		if(selection.equals("")) {
			return handleGet();
		}
		else {
			List<Long> ids = new ArrayList<>();
			for (String idString : selection.split(",")) {
				ids.add(Long.parseLong(idString));
			}
				computerServices.deleteComputer(ids);
				return handleGet();	
		}
	}
}
