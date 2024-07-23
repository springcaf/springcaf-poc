package com.springcaf.starter.app.controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springcaf.core.exception.SpringcafException;

@Controller
public class FrameworkErrorController implements ErrorController  {
 
	static Logger logger = LogManager.getLogger(FrameworkErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request,
    		HttpServletResponse response, 
			Model model,
			Exception ex) throws SpringcafException, SQLException, IOException {
    	
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
         
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
            	logger.debug(statusCode);
                //return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            	logger.debug(statusCode);
                //return "error-500";
            }
        }

		model.addAttribute("stacktrace", status);
		model.addAttribute("url", "");
		model.addAttribute("title", "error occurred");

        return "common/error";
    }
}