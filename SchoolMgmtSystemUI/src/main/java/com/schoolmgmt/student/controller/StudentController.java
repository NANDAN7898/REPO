package com.schoolmgmt.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController
{
	@GetMapping("/getname")
	public String getName()
	{
		return "adminHome";
	}
}
