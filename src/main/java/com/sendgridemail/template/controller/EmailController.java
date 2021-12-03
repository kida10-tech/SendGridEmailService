package com.sendgridemail.template.controller;

import com.sendgridemail.template.service.implementation.EmailServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/email")
public class EmailController {

	@Autowired
	EmailServiceImplementation emailServiceImplementation;

	@PostMapping("/send-template")
	public String sendWithTemplate() throws IOException {
		return emailServiceImplementation.sendTemplate();
	}

}
