package org.sid.springmvc.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.classic.Logger;

@Controller
public class SecurityController {
	
	
	
	@GetMapping("/notAuthorized")
	public String error() {
		return "notAuthorized";
	}
	

	@PostMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		return "redirect:/login";
	}
	  // Login form with error
	@RequestMapping("/login")
	  public String loginError(Model model) {
	    model.addAttribute("Loginerror", true);
	    return "login";
	  }

}
