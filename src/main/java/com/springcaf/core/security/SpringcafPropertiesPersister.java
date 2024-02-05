package com.springcaf.core.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.DefaultPropertiesPersister;

import com.springcaf.core.security.util.EncryptUtils;
import com.springcaf.core.util.StringUtils;

public class SpringcafPropertiesPersister extends DefaultPropertiesPersister {
	
	private String encryptKey = null;	// 16 bytes
	private String encryptInitVector = null; // 16 bytes
	private String[] encryptedProps = null;
	
	static Logger logger = LogManager.getLogger(SpringcafPropertiesPersister.class);
	
	/**
	 * Constructor
	 * @param encryptKey
	 * @param encryptedProps
	 */
	public SpringcafPropertiesPersister(String encryptKey, String encryptInitVector, String[] encryptedProps)
	{
		this.encryptKey = encryptKey;
		this.encryptInitVector = encryptInitVector;
		this.encryptedProps = encryptedProps;
	}
	
	
	@Override
	public void load(Properties props, InputStream is) throws IOException {
		super.load(props, is);
		// decrypt the encrypted values
		this.decryptEncryptedProps(props);
	}

	@Override
	public void load(Properties props, Reader reader) throws IOException {
		super.load(props, reader);
		// decrypt the encrypted values
		this.decryptEncryptedProps(props);
	}

	private void decryptEncryptedProps(Properties props)
	{
		try
		{
			if(StringUtils.isNullOrEmpty(encryptKey) || this.encryptedProps == null || this.encryptedProps.length == 0)
			{
				return;
			}
			
			for(String propertyName : this.encryptedProps)
			{
				String value = props.getProperty(propertyName);
				if(value != null)
				{
					String decryptedValue = EncryptUtils.decrypt(encryptKey, encryptInitVector, value);
					props.setProperty(propertyName, decryptedValue);
				}
			}
		}
		catch(Exception ex)
		{
			logger.error("decrypt failed", ex);
		}
	}
	
	/**
	 * Encrypt a property value
	 * @param input
	 * @return
	 */
	public String encryptValue(String input)
	{
		try
		{
			String encryptedValue = EncryptUtils.encrypt(encryptKey, encryptInitVector, input);
			return encryptedValue;
		}
		catch(Exception ex)
		{
			logger.error("encrypt failed", ex);
		}
		
		return input;
	}

	/**
	 * Decrypt a value
	 * @param input
	 * @return
	 */
	public String decryptValue(String input)
	{
		try
		{
			String decryptedValue = EncryptUtils.decrypt(encryptKey, encryptInitVector, input);
			return decryptedValue;
		}
		catch(Exception ex)
		{
			logger.error("encrypt failed", ex);
		}
		
		return input;
	}
}
