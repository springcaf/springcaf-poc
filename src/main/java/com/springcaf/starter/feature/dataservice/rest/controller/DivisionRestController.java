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
import com.springcaf.starter.feature.dataservice.jdbc.model.Division;
import com.springcaf.starter.feature.dataservice.jdbc.service.DivisionDataService;

@RestController
public class DivisionRestController extends DefaultRestServiceImpl {

	@Autowired
	DivisionDataService divisionDataService;
	
	/**
	 * Create a new division record
	 * @param division
	 * @param reqHeaders
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	@PostMapping(value = "/division")
	public ResponseEntity<Division> creationDivision(@RequestBody Division division, @RequestHeader HttpHeaders reqHeaders) throws SpringcafException, SQLException
	{
		String userId = this.getValidUserId(reqHeaders);
		divisionDataService.saveDivision(division, userId);
		
		return new ResponseEntity<Division>(division, HttpStatus.OK);
	}

	/**
	 * Select a division record by primary key
	 * @param divisionId
	 * @param reqHeaders
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	@GetMapping(value = "/division/{divisionId}")
	public ResponseEntity<Division> getDivision(@PathVariable("divisionId") Integer divisionId, @RequestHeader HttpHeaders reqHeaders) throws SpringcafException, SQLException
	{
		Division division = divisionDataService.findDivisionByPk(divisionId);
		
		if(division == null)
		{
			return new ResponseEntity<Division>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Division>(division, HttpStatus.OK);
	}

	/**
	 * Update a division record
	 * @param divisionId
	 * @param division
	 * @param reqHeaders
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	@PutMapping(value = "/division/{divisionId}")
	public ResponseEntity<Boolean> updateDivision(@PathVariable("divisionId") Integer divisionId, @RequestBody Division division, @RequestHeader HttpHeaders reqHeaders) throws SpringcafException, SQLException
	{
		String userId = this.getValidUserId(reqHeaders);
		divisionDataService.saveDivision(division, userId);
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}	
	
	/**
	 * Delete a division record by primary key
	 * @param divisionId
	 * @param reqHeaders
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	@DeleteMapping(value = "/division/{divisionId}")
	public ResponseEntity<Boolean> deleteDivision(@PathVariable("divisionId") Integer divisionId, @RequestHeader HttpHeaders reqHeaders) throws SpringcafException, SQLException
	{
		divisionDataService.deleteDivisionByPk(divisionId);

		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}	
	
	@GetMapping(value = "/division")
	public ResponseEntity<List<Division>> getAllDivisions(@RequestHeader HttpHeaders reqHeaders) throws SpringcafException, SQLException
	{
		List<Division> divisions = divisionDataService.findAllDivision();
		
		if(divisions == null || divisions.size() == 0)
		{
			return new ResponseEntity<List<Division>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Division>>(divisions, HttpStatus.OK);
	}

}
