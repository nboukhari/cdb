package com.excilys.cdb2.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb2.exception.ValidationException;

@Controller
@RequestMapping("/Login")
public class LoginController {
	@GetMapping
	public String handleGet(Model model) throws ClassNotFoundException, IOException, ValidationException {
		
		return "Login";
	}

}
