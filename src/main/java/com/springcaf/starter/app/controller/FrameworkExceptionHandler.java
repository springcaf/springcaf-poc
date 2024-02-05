package com.springcaf.starter.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springcaf.starter.app.nav.MainNavUrlConstants;
import com.springcaf.starter.app.service.ApplicationFrameworkService;

@ControllerAdvice
public class FrameworkExceptionHandler {
	
	@Autowired
	ApplicationFrameworkService applicationFrameworkService;
	
	static Logger logger = LogManager.getLogger(FrameworkExceptionHandler.class);

    // Total control - setup a model and return the view name yourself. Or
    // consider subclassing ExceptionHandlerExceptionResolver (see below).
    @ExceptionHandler(Exception.class)
	public String handleError(HttpServletRequest request,
			HttpServletResponse response, 
			Model model,
			Exception ex) {

    	try
    	{
    		// Page Widget setup
    		applicationFrameworkService.setupPageWidget(model, request, response, MainNavUrlConstants.APPLICATION_HOME_MENU);
    		ex.printStackTrace();
    	}
    	catch(Exception ex2)
    	{
    		// do not handle
    		ex2.printStackTrace();
    	}

		logger.error("Request: " + request.getRequestURL() + " raised " + ex);

		model.addAttribute("stacktrace", ex);
		model.addAttribute("url", request.getRequestURL());
		model.addAttribute("title", "error occurred");

		return "common/error";
	}

}
