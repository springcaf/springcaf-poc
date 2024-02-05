package com.springcaf.starter.feature.dataservice.rest.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.starter.common.dataservice.rest.service.DefaultRestServiceImpl;
import com.springcaf.starter.feature.dataservice.jdbc.model.UsState;
import com.springcaf.starter.feature.dataservice.jdbc.service.UsStateDataService;

@RestController
public class UsStateRestController extends DefaultRestServiceImpl {

	@Autowired
	UsStateDataService usStateDataService;
	
	/**
	 * Create a new usState record
	 * @param usState
	 * @param reqHeaders
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	@PostMapping(value = "/usstate")
	public ResponseEntity<UsState> creationUsState(@RequestBody UsState usState, @RequestHeader HttpHeaders reqHeaders) throws SpringcafException, SQLException
	{
		String userId = this.getValidUserId(reqHeaders);
		usStateDataService.saveUsState(usState, userId);
		
		return new ResponseEntity<UsState>(usState, HttpStatus.OK);
	}

	/**
	 * Select a usState record by primary key
	 * @param stateCode
	 * @param reqHeaders
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	@GetMapping(value = "/usstate/{stateCode}")
	public ResponseEntity<UsState> getUsState(@PathVariable("stateCode") String stateCode, @RequestHeader HttpHeaders reqHeaders) throws SpringcafException, SQLException
	{
		UsState usState = usStateDataService.findUsStateByPk(stateCode);
		
		if(usState == null)
		{
			return new ResponseEntity<UsState>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<UsState>(usState, HttpStatus.OK);
	}

	/**
	 * Update a usState record
	 * @param stateCode
	 * @param usState
	 * @param reqHeaders
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	@PutMapping(value = "/usstate/{stateCode}")
	public ResponseEntity<Boolean> updateUsState(@PathVariable("stateCode") String stateCode, @RequestBody UsState usState, @RequestHeader HttpHeaders reqHeaders) throws SpringcafException, SQLException
	{
		String userId = this.getValidUserId(reqHeaders);
		usStateDataService.saveUsState(usState, userId);
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}	
	
	/**
	 * Delete a usState record by primary key
	 * @param stateCode
	 * @param reqHeaders
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	@DeleteMapping(value = "/usstate/{stateCode}")
	public ResponseEntity<Boolean> deleteUsState(@PathVariable("stateCode") String stateCode, @RequestHeader HttpHeaders reqHeaders) throws SpringcafException, SQLException
	{
		usStateDataService.deleteUsStateByPk(stateCode);

		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}	
	
	@GetMapping(value = "/usstate")
	public ResponseEntity<List<UsState>> getAllUsStates(@RequestHeader HttpHeaders reqHeaders) throws SpringcafException, SQLException
	{
		List<UsState> usStates = usStateDataService.findAllUsState();
		
		if(usStates == null || usStates.size() == 0)
		{
			return new ResponseEntity<List<UsState>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<UsState>>(usStates, HttpStatus.OK);
	}

}
