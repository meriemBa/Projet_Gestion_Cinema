package org.sid.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sid.dao.RoleRepository;
import org.sid.dao.UserRepository;
import org.sid.dao.UserRoleRepository;
import org.sid.entities.User;
import org.sid.entities.Ville;
import org.sid.entities.roles;
import org.sid.entities.user_roles;
import org.sid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.bytebuddy.utility.RandomString;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/listUsers")
	public String utilisateur(Model model,roles role,
		@RequestParam(name="page",defaultValue = "0") int page,
		@RequestParam(name="size",defaultValue = "5") int size,
		@RequestParam(name="keyword",defaultValue = "")String mc
	){
		Page<User> pageUser=userRepository.findByUsernameContains(mc,PageRequest.of(page, size));
		model.addAttribute("listUsers",pageUser.getContent());
		model.addAttribute("pages",new int[pageUser.getTotalPages()]);
		
		model.addAttribute("currentPage",page);
		model.addAttribute("size",size);
		model.addAttribute("keyword",mc);
		model.addAttribute("role",role);
		
		return "users";
	}
	
	  @GetMapping(path="/deleteUser")
	  public String delete(Long id,String keyword,int page,int size) {
		 
		  userRepository.deleteById(id);

		  return "redirect:/listUsers"; 
	  }
	  @GetMapping(path="/formUser")
	  public String formUser(Model model) {
		  model.addAttribute("user",new User());
		model.addAttribute("role",roleRepository.findAll());
			
		  return "formUser"; 
	  }
	
	  @PostMapping(path="/saveUser")
	  public String saveUser( User user) {
		  String role="HI";
		  user.setPassword(passwordEncoder.encode(user.getPassword()));
		  user_roles userRole=new user_roles();
		  if(user.getRole().getId()==1) {
			  role="ADMIN";
		  }
		  else if(user.getRole().getId()==2) {
			  role="CONSULTED";
		  }
		  else  {
			  role="MANAGER";
		  }
		  userRole.setUsername(user.getUsername()); userRole.setRole(role);
		  userRoleRepository.save(userRole);
		  userRepository.save(user);
		  return "confirmationUser"; 
	  }
	  
	  @GetMapping(path="/editUser")
	  public String editUser(Model model ,Long id) {
		  User user =userRepository.findById(id).get();
		  model.addAttribute("role",roleRepository.findAll());
		  model.addAttribute("user",user);
		  return "formUser"; 
	  }
	  
	 
}
