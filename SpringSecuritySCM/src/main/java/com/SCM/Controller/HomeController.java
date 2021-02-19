package com.SCM.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.SCM.Dao.UserRepository;
import com.SCM.Entities.User;
import com.SCM.Helper.Message;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title","Home Smart Contact Manager");
		return "home";
	}

	
	@RequestMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title","About Smart Contact Manager");
		return "about";
	}
	
	
	@RequestMapping("/signup")
	public String singup(Model model)
	{
		model.addAttribute("title","Register Smart Contact Manager");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	//Handler for registration

	//@PostMapping("//do_register")
	//if we write this then we donot declare method
	 //   OR
	
	
	@RequestMapping(value = "/do_register",method= RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,@RequestParam(value = "agreement",defaultValue = "false")boolean agreement,Model model,HttpSession session)
	{
		
		try {
			
			if(!agreement)
			{
				System.out.println("You have not agree the terms and condition");
				throw new Exception("You have not agree the terms and condition");
			}
			
			if(result1.hasErrors())
			{
				System.out.println("Errors "+result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			
			
			
			System.out.println("Agreement:- "+agreement);
			System.out.println("User"+user);
			
			User result = this.userRepository.save(user);

			model.addAttribute("user", new User());

			session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
			return "signup";
			
		} 
		
		catch (Exception e)
		{
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong..!!"+e.getMessage(), "alert-danger"));
			return "signup";
		}
	}
	
	
	
	
	}
