package in.webproto.schoolmgmt.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController
{
	private static final Logger OUT = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping(value = {"/admin"})
	public String admin(HttpServletRequest request)
	{
		request.setAttribute("msg", "School Mgmt System");
		OUT.debug("Redirected to DashboardScreen");
		return "DashboardScreen";
	}
	
	@GetMapping("/login")
	public String login()
	{
		
		return "LoginScreen";
	}
}
