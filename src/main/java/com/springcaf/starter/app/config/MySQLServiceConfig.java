package com.springcaf.starter.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springcaf.core.jdbc.service.AbstractDataService;
import com.springcaf.starter.feature.dataservice.jdbc.service.BrandDataService;
import com.springcaf.starter.feature.dataservice.jdbc.service.BrandDataServiceImpl;
import com.springcaf.starter.feature.dataservice.jdbc.service.DepartmentDataService;
import com.springcaf.starter.feature.dataservice.jdbc.service.DepartmentDataServiceImpl;
import com.springcaf.starter.feature.dataservice.jdbc.service.DivisionDataService;
import com.springcaf.starter.feature.dataservice.jdbc.service.DivisionDataServiceImpl;
import com.springcaf.starter.feature.dataservice.jdbc.service.PersonDataService;
import com.springcaf.starter.feature.dataservice.jdbc.service.PersonDataServiceImpl;
import com.springcaf.starter.feature.dataservice.jdbc.service.UsStateDataService;
import com.springcaf.starter.feature.dataservice.jdbc.service.UsStateDataServiceImpl;

@Configuration
public class MySQLServiceConfig implements WebMvcConfigurer {
	
	private static final String overrideSchema = "starterdb";

	@Autowired
	AbstractDataService databaseService;
	
	@Bean 
	public DivisionDataService getDivisionDataService()
	{
		return new DivisionDataServiceImpl(databaseService, overrideSchema);
	}
	
	@Bean 
	public DepartmentDataService getDepartmentDataService()
	{
		return new DepartmentDataServiceImpl(databaseService, overrideSchema);
	}

	@Bean 
	public BrandDataService getBrandDataService()
	{
		return new BrandDataServiceImpl(databaseService, overrideSchema);
	}

	@Bean 
	public UsStateDataService getUsStateDataService()
	{
		return new UsStateDataServiceImpl(databaseService, overrideSchema);
	}
	
	@Bean 
	public PersonDataService getPersonDataService()
	{
		return new PersonDataServiceImpl(databaseService, overrideSchema);
	}

}
