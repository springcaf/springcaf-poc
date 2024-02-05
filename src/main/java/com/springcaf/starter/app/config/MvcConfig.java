package com.springcaf.starter.app.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springcaf.starter.app.service.ApplicationFrameworkService;
import com.springcaf.starter.app.service.LoginPasswordAuthenticationProvider;
import com.springcaf.starter.app.service.MySQLDataService;
import com.springcaf.starter.feature.service.FeatureDropDownListService;
import com.springcaf.starter.feature.service.FeatureDropDownListServiceImpl;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello").setViewName("home/hello");
    }
    
    @Bean
    public ApplicationFrameworkService applicationFrameworkService() {
        return new ApplicationFrameworkService();
    }
    
    @Bean(name = "readerDataSource")
    @ConfigurationProperties(prefix = "spring.readerdatasource")
    public DataSource readerDataSource() 
    {
    	return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "writerDataSource")
    @ConfigurationProperties(prefix = "spring.writerdatasource")
    public DataSource writerDataSource() 
    {
    	return DataSourceBuilder.create().build();
    }
    
	@Bean
	public MySQLDataService databaseService()
	{
		return new MySQLDataService();
	}
	
	@Bean
	public LoginPasswordAuthenticationProvider getLoginPasswordAuthenticationProvider()
	{
		return new LoginPasswordAuthenticationProvider();
	}
	
	@Bean
	public FeatureDropDownListService getFeatureDropDownListService()
	{
		return new FeatureDropDownListServiceImpl();
	}
}
