package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class HomeResource {

	@GetMapping
	public String index() {
		return "Welcome to the Spring JEE Application!";
	}
}
