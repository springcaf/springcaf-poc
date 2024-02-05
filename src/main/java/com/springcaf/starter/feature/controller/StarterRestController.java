package com.springcaf.starter.feature.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcaf.core.web.model.KeyValueListModel;
import com.springcaf.core.web.model.WebElementModel;
import com.springcaf.core.web.model.element.DropDownListElement;
import com.springcaf.core.web.model.form.WebFormModel;
import com.springcaf.core.web.util.WidgetUtils;
import com.springcaf.starter.app.nav.MainNavUrlConstants;
import com.springcaf.starter.feature.service.FeatureDropDownListService;

@RestController
public class StarterRestController implements MainNavUrlConstants {
	
	@Autowired
	FeatureDropDownListService featureDropDownListService;
	
	/**
	 * Retrieve the list of column names to be mapped
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = URL_DROPDOWN_REFRESH_API + "/{refkey}/{refvalue}", method = RequestMethod.GET)
	public ResponseEntity<String> dropDownListRefresh(@PathVariable("refkey") String refKey, @PathVariable("refvalue") String refValue, @RequestHeader HttpHeaders reqHeaders) throws Exception
	{
		Map<String, String> elementRefreshList = new HashMap<String, String>();
		
		WebFormModel formModel = featureDropDownListService.getRegisteredModel(refKey);
		WebElementModel currentElement = formModel.getWebElementByFormElementId(refKey);
		
		if(currentElement != null)
		{
			List<String> dependentList = formModel.getDirectElementsDependingOnThis(currentElement.getElementName());
			List<String> subDependentList = formModel.getIndirectElementsDependingOnThis(currentElement.getElementName());

			for(String name : dependentList)
			{
				String subRefKey = formModel.getFormName() + "_" + name;
				WebElementModel subRefElement = formModel.getWebElementByFormElementId(subRefKey);
				
				if(subRefElement != null && subRefElement instanceof DropDownListElement)
				{
					KeyValueListModel list = featureDropDownListService.getDropdownListByRef(subRefKey, refValue);
					boolean addBlank = ((DropDownListElement)subRefElement).isAddBlankRow();
					elementRefreshList.put(subRefKey, WidgetUtils.renderDropdownListOptions(list, addBlank, null));
				}
				else
				{
					elementRefreshList.put(subRefKey, "");
				}
			}
			
			for(String name : subDependentList)
			{
				elementRefreshList.put(formModel.getFormName() + "_" + name, "");
			}

		}
		
		ObjectMapper mapper = new ObjectMapper();

		//Object to JSON in String
		String jsonInString = mapper.writeValueAsString(elementRefreshList);

		if(elementRefreshList.isEmpty()){
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(jsonInString, HttpStatus.OK);
	}

}
