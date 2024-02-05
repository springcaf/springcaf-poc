package com.springcaf.core.exception;

public class SpringcafException extends Exception {

	/**
	 * generated serial ID
	 */
	private static final long serialVersionUID = 1820376234348486331L;

	/**
	 * Constructor
	 */
	public SpringcafException()
	{
		// default
	}
	
	/**
	 * Constructor
	 * @param message
	 */
	public SpringcafException(String message)
	{
		super(message);
	}
	
	/**
	 * Constructor
	 * @param ex
	 */
	public SpringcafException(Exception ex)
	{
		super(ex);
	}
}
