package com.adiaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.adiaz.entities.UsersVO;
import com.adiaz.forms.UserFormValidator;
import com.adiaz.services.UsersManager;
import com.adiaz.utils.UtilsLegaSport;

@Controller
@RequestMapping ("/users")
public class UsersController {

	@Autowired UsersManager usersManager;
	@Autowired UserFormValidator userFormValidator;

	@RequestMapping("/list")
	public ModelAndView usersList(
			@RequestParam(value="update_done", defaultValue="false") boolean updateDone,
			@RequestParam(value="add_done", defaultValue="false") boolean addDone,
			@RequestParam(value="remove_done", defaultValue="false") boolean removeDone) {
		ModelAndView modelAndView = new ModelAndView("users_list");
		modelAndView.addObject("users", usersManager.queryAllUsers());
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("add_done", addDone);
		return modelAndView;
	}

	@RequestMapping("/add")
	public ModelAndView addUser() {
		ModelAndView modelAndView = new ModelAndView("users_add");
		modelAndView.addObject("my_form", new UsersVO());
		return modelAndView;
	}
	
	@RequestMapping("/doAdd")
	public ModelAndView doAddUser(@ModelAttribute("my_form") UsersVO userVO, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		userFormValidator.validate(userVO, bindingResult);
		if (!bindingResult.hasErrors()) {
			try {
				userVO.setPassword(UtilsLegaSport.sha256Encode(userVO.getPassword01()));
				userVO.setEnabled(true);
				userVO.setAccountNonExpired(true);
				usersManager.addUser(userVO);
			} catch (Exception e) {			
				e.printStackTrace();
			}
			String viewName = "redirect:/users/list";
			viewName += "?add_done=true";
			modelAndView.setViewName(viewName);
		} else {
			modelAndView.addObject("my_form", userVO);
			modelAndView.setViewName("users_add");
		}		
		return modelAndView;
	}
	
	@RequestMapping("/update")
	public ModelAndView updateUser(@RequestParam String userName) {
		ModelAndView modelAndView = new ModelAndView("users_update");
		modelAndView.addObject("my_form", usersManager.queryUserByName(userName));
		return modelAndView;
	}
	
	@RequestMapping("/doUpdate")
	public ModelAndView doUpdateUser(@ModelAttribute("my_form") UsersVO userVO, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		userFormValidator.validate(userVO, bindingResult);
		if (!bindingResult.hasErrors()) {
			try {
				if (userVO.isUpdatePassword()) {
					userVO.setPassword(UtilsLegaSport.sha256Encode(userVO.getPassword01()));
				} else {
					UsersVO originalUser = usersManager.queryUserByName(userVO.getUsername());
					userVO.setPassword(originalUser.getPassword());
				}
				usersManager.updateUser(userVO);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String viewName = "redirect:/users/list";
			viewName += "?update_done=true";
			modelAndView.setViewName(viewName);
		} else {
			modelAndView.addObject("my_form", userVO);
			modelAndView.setViewName("users_update");
		}		
		return modelAndView;
	}
	
	@RequestMapping("/doDelete")
	public ModelAndView doDelete (@RequestParam String userName) {
		ModelAndView modelAndView = new ModelAndView();
		String viewName = "redirect:/users/list";
		try {
			usersManager.removeUser(userName);
			viewName += "?remove_done=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelAndView.setViewName(viewName);
		return modelAndView;
	}
	
}
