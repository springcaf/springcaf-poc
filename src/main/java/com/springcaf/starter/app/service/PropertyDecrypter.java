package com.springcaf.starter.app.service;

import com.springcaf.core.security.SpringcafPropertiesPersister;

public class PropertyDecrypter extends SpringcafPropertiesPersister {
	
	private static String encryptKey = "myWebTools123456";
	private static String encryptInitVector = "123456MyWebTools";
	private static String[] listOfKeysToEncrypt = new String[] {"jdbc.password"};
	
	/**
	 * Constructor
	 */
	public PropertyDecrypter()
	{
		super(encryptKey, encryptInitVector, listOfKeysToEncrypt);
	}

}
