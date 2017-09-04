package com.adiaz.controllers;

import com.adiaz.entities.Category;
import com.adiaz.forms.CategoriesForm;
import com.adiaz.forms.validators.CategoriesFormValidator;
import com.adiaz.services.CategoriesManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@SessionAttributes({"categories"})
@RequestMapping("/categories")
public class CategoriesController {

	public static final Logger logger = Logger.getLogger(CategoriesController.class);

	@Autowired CategoriesManager categoriesManager;
	@Autowired CategoriesFormValidator categoriesFormValidator;

	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(value = "add_done", defaultValue = "false") boolean addDone,
			@RequestParam(value = "update_done", defaultValue = "false") boolean updateDone,
			@RequestParam(value = "remove_done", defaultValue = "false") boolean removeDone) {
		ModelAndView modelAndView = new ModelAndView("categories_list");
		/* It is necessary to update the categories list in session.*/
		List<Category> categoryList = categoriesManager.queryCategories();
		modelAndView.addObject("categories", categoryList);
		modelAndView.addObject("add_done", addDone);
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("update_done", updateDone);
		return modelAndView;
	}

	@RequestMapping("/view")
	public ModelAndView view(@RequestParam(value = "idCategory") Long idCategory){
		ModelAndView modelAndView = new ModelAndView("categories_view");
		CategoriesForm categoriesForm = categoriesManager.queryCategoriesFormById(idCategory);
		modelAndView.addObject("my_form", categoriesForm);
		return modelAndView;
	}

	@RequestMapping("/update")
	public ModelAndView update(@RequestParam(value = "idCategory") Long idCategory){
		ModelAndView modelAndView = new ModelAndView("categories_update");
		CategoriesForm categoriesForm = categoriesManager.queryCategoriesFormById(idCategory);
		modelAndView.addObject("my_form", categoriesForm);
		return modelAndView;
	}

	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(@ModelAttribute("my_form") CategoriesForm categoriesForm, BindingResult bindingResult) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		categoriesFormValidator.validate(categoriesForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", categoriesForm);
			modelAndView.setViewName("category_update");
		} else {
			categoriesManager.update(categoriesForm);
			String viewName = "redirect:/categories/list?update_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/add")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("categories_add");
		modelAndView.addObject("my_form", new CategoriesForm());
		return modelAndView;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(@ModelAttribute("my_form") CategoriesForm categoriesForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("categories_list");
		categoriesFormValidator.validate(categoriesForm, bindingResult);
		if (!bindingResult.hasErrors()) {
			try {
				categoriesManager.add(categoriesForm);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			String viewName = "redirect:/categories/list";
			viewName += "?add_done=true";
			modelAndView.setViewName(viewName);
		} else {
			modelAndView.addObject("my_form", categoriesForm);
			modelAndView.setViewName("categories_add");
		}
		return modelAndView;
	}

	@RequestMapping("/doDelete")
	public String doDelete(@RequestParam Long id) throws Exception {
		// TODO: 25/07/2017 validate category has not in a competition.
		categoriesManager.remove(id);
		return "redirect:/categories/list?remove_done=true";
	}
}
