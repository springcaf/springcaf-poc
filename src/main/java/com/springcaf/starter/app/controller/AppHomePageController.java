package com.springcaf.starter.app.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.widget.WidgetConstants;
import com.springcaf.starter.app.nav.MainNavUrlConstants;
import com.springcaf.starter.app.service.ApplicationFrameworkService;

@Controller
public class AppHomePageController implements WidgetConstants {

    @Autowired
    ApplicationFrameworkService applicationFrameworkService;

    @RequestMapping("/")
    String index(HttpServletRequest request,
                 HttpServletResponse response,
                 Model model) throws SpringcafException, SQLException, IOException {

        // Application setup
        applicationFrameworkService.setupPageWidget(model, request, response, MainNavUrlConstants.APPLICATION_HOME_MENU);

        return "app/app-home-page";
    }

}

