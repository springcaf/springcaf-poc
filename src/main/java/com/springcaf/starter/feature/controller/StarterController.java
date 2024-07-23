package com.springcaf.starter.feature.controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.model.KeyValueListModel;
import com.springcaf.core.web.util.WebRequestUtils;
import com.springcaf.starter.app.meta.ApplicationMeta;
import com.springcaf.starter.app.nav.MainNavUrlConstants;
import com.springcaf.starter.app.service.ApplicationFrameworkService;
import com.springcaf.starter.feature.dataservice.jdbc.model.Person;
import com.springcaf.starter.feature.dataservice.jdbc.service.PersonDataService;
import com.springcaf.starter.feature.dataservice.jdbc.service.UsStateDataService;
import com.springcaf.starter.feature.service.FeatureDropDownListService;
import com.springcaf.starter.feature.ui.model.DropdownInput;
import com.springcaf.starter.feature.ui.model.AddressInput;
import com.springcaf.starter.feature.ui.widget.DropdownInputWidget;
import com.springcaf.starter.feature.ui.widget.PersonInputWidget;
import com.springcaf.starter.feature.ui.widget.AddressInputWidget;

@Controller
public class StarterController implements MainNavUrlConstants {

    @Autowired
    ApplicationFrameworkService applicationFrameworkService;
    
    @Autowired
    UsStateDataService usStateDataService;
    
    @Autowired
    FeatureDropDownListService featureDropDownListService;

    @Autowired
    PersonDataService personDataService;

    @RequestMapping(URL_PAGE_1)
    String page1(HttpServletRequest request,
                 HttpServletResponse response,
                 Model model) throws SpringcafException, SQLException, IOException {

        // Application setup
        applicationFrameworkService.setupPageWidget(model, request, response, MainNavUrlConstants.APPLICATION_HOME_MENU);
		ApplicationMeta appMeta = new ApplicationMeta(applicationFrameworkService, request, response);
		String postUrl = URL_PAGE_1;

		////////////////////////////////////////////////
		// form 1 on the page
		////////////////////////////////////////////////
		AddressInput entity1 = new AddressInput();
		KeyValueListModel usStateList = usStateDataService.getUsStateList();
		AddressInputWidget addressInputForm = new AddressInputWidget(appMeta, request, postUrl, entity1);
		addressInputForm.addKeyValueList("usStateList", usStateList);
		// preload some values
		entity1.setState("TX");
		entity1.setZip("75354");
		entity1.setPrimary(true);
		
		////////////////////////////////////////////////
		// form 2 on the page
		////////////////////////////////////////////////
		String baseRestUrl = request.getContextPath() + URL_DROPDOWN_REFRESH_API;
		DropdownInput ddEntity = new DropdownInput();
		KeyValueListModel divList = featureDropDownListService.getDivisionList();
		KeyValueListModel quarterList = featureDropDownListService.getQuarterList();
		DropdownInputWidget dropdownInputForm = new DropdownInputWidget(appMeta, request, postUrl, ddEntity);
		dropdownInputForm.addKeyValueListAndRestUrl("divisionIdList", divList, baseRestUrl);
		dropdownInputForm.addKeyValueListAndRestUrl("departmentIdList", null, baseRestUrl);
		dropdownInputForm.addKeyValueListAndRestUrl("quarterIdList", quarterList, null);
		
		////////////////////////////////////////////////
		// handle form post
		////////////////////////////////////////////////
		if(WebRequestUtils.isPostback(request))
		{
			// register form 1
			addressInputForm.handleFormPost(request, response);
			
			// register form 2
			dropdownInputForm.handleFormPost(request, response);
		}
		
		////////////////////////////////////////////////
		// Inject forms to the page
		////////////////////////////////////////////////
		addressInputForm.setEditMode(true);
		model.addAttribute("section1", addressInputForm.renderToHtml());
		dropdownInputForm.setEditMode(true);
		model.addAttribute("section2", dropdownInputForm.renderToHtml());

		return "common/standard-third-page";
    }

    @RequestMapping(URL_PAGE_2+"/{personId}")
    String page2(@PathVariable("personId") Integer personId,
    			 HttpServletRequest request,
                 HttpServletResponse response,
                 Model model) throws SpringcafException, SQLException, IOException {

        // Application setup
        applicationFrameworkService.setupPageWidget(model, request, response, MainNavUrlConstants.APPLICATION_HOME_MENU);
		ApplicationMeta appMeta = new ApplicationMeta(applicationFrameworkService, request, response);
		String postUrl = URL_PAGE_2 + "/" + personId;

		////////////////////////////////////////////////
		// person form on the page
		////////////////////////////////////////////////
		Person person =personDataService.findPersonByPk(personId);
		if(person == null)
		{
			person = new Person();
		}
		KeyValueListModel usStateList = usStateDataService.getUsStateList();
		PersonInputWidget personInputWidget = new PersonInputWidget(appMeta, request, postUrl, person);
		personInputWidget.addKeyValueList("usStateList", usStateList);

		////////////////////////////////////////////////
		// handle form post
		////////////////////////////////////////////////
		if(WebRequestUtils.isPostback(request))
		{
			// register form 
			personInputWidget.handleFormPost(request, response, personDataService);
			
		}
		
		////////////////////////////////////////////////
		// Inject forms to the page
		////////////////////////////////////////////////
		personInputWidget.setEditMode(true);
		model.addAttribute("section1", personInputWidget.renderToHtml());

		return "common/standard-third-page";
    }


}
