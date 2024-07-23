package com.springcaf.starter.app.controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.starter.app.nav.MainNavUrlConstants;
import com.springcaf.starter.app.service.ApplicationFrameworkService;

@Controller
public class FrameworkMainController {
	
	@Autowired
	ApplicationFrameworkService applicationFrameworkService;
	
    @GetMapping("/inprogress")
    String inProgress(HttpServletRequest request, 
			HttpServletResponse response, 
			Model model) throws SpringcafException, SQLException, IOException {
    	
		// Page Widget setup
		applicationFrameworkService.setupPageWidget(model, request, response, MainNavUrlConstants.APP_SUB_INPROGRESS_PAGE_MENU);
		
		model.addAttribute("xyz", "test attribute");

        return "common/in-progress-page";
    }

    @RequestMapping("/login")
    String login(HttpServletRequest request, 
			HttpServletResponse response, 
			Model model) throws SpringcafException, SQLException, IOException {
    	
		// Page Widget setup
		applicationFrameworkService.setupPageWidget(model, request, response, MainNavUrlConstants.APP_SUB_LOGIN_MENU);

        return "common/login";
    }
    
    @GetMapping("/noaccess")
    String noAccess(HttpServletRequest request, 
			HttpServletResponse response, 
			Model model) throws SpringcafException, SQLException, IOException {
    	
		// Page Widget setup
		model.addAttribute("xyz", "test attribute");

        return "common/no-access-page";
    }

}