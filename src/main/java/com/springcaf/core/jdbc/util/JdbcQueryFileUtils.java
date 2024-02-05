package com.springcaf.core.jdbc.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.service.QueryContainer;
import com.springcaf.core.util.StringUtils;

public class JdbcQueryFileUtils {
	
	private static final String LINE_FEED = "\r\n";
	
	public static QueryContainer loadQueryFileIntoContainer(String fileName) throws IOException, SpringcafException
	{
		QueryContainer container = new QueryContainer();
		
		// parse the input query file
		FileReader fr = new FileReader(fileName);
		LineNumberReader lnr = new LineNumberReader(fr);
		
		String line = null;
		StringBuffer buffer = null;
		String queryName = null;
		boolean inComment = false;
		while((line=lnr.readLine()) != null)
		{
			if(!inComment && line.trim().startsWith("/*"))
			{
				inComment = true;
			}
			
			if(containsQueryName(line))
			{
				if(queryName != null && buffer != null)
				{
					container.addNamedQuery(queryName, StringUtils.trimLastStringBlock(buffer.toString(), LINE_FEED));
				}
				queryName = getQueryName(line);
				buffer = new StringBuffer();
				
				String remainingString = getQueryNameRemainingString(line);
				
				if(!StringUtils.isNullOrEmpty(remainingString))
				{
					buffer.append(remainingString + LINE_FEED);
				}
			}
			else if(inComment || line.trim().startsWith("//"))
			{
				// skip
			}
			else
			{
				buffer.append(line + LINE_FEED);
			}
			
			// check comments
			if(inComment && line.trim().endsWith("*/"))
			{
				inComment = false;
			}
		}
		
		if(queryName != null && buffer != null)
		{
			container.addNamedQuery(queryName, StringUtils.trimLastStringBlock(buffer.toString(), LINE_FEED));
		}
		
		lnr.close();
		fr.close();
		
		return container;
	}

	/**
	 * Identify if a line contains the query name, in the format of [query_name]=
	 * @param line
	 * @return
	 */
	private static boolean containsQueryName(String line)
	{
		if(StringUtils.isNullOrEmpty(line))
		{
			return false;
		}
		
		// remove all spaces
		line = line.replaceAll(" ", "");
		
		if(line.charAt(0) == '[' && line.indexOf("]=") > 0)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Get the query name.
	 * @param line
	 * @return
	 * @throws SpringCafJdbcException 
	 */
	private static String getQueryName(String line) throws SpringcafException
	{
		if(!containsQueryName(line))
		{
			throw new SpringcafException("This is not a valid string containing a query name");
		}
		
		for(int i=0; i < line.length(); i++)
		{
			if(line.charAt(i) == ']')
			{
				return line.substring(1, i).trim();
			}
		}
		
		return null;
	}
	
	/**
	 * Get the remaining string from the query name line (if any)
	 * @param line
	 * @return
	 * @throws SpringCafJdbcException 
	 */
	private static String getQueryNameRemainingString(String line) throws SpringcafException
	{
		if(!containsQueryName(line))
		{
			throw new SpringcafException("This is not a valid string containing a query name");
		}
		
		int startPos = 0;
		for(int i=0; i < line.length(); i++)
		{
			if(line.charAt(i) == ']')
			{
				startPos = i;
				continue;
			}
		}
		
		for(int i=startPos; i < line.length(); i++)
		{
			if(line.charAt(i) == '=')
			{
				return line.substring(i+1); 
			}
		}
		
		return "";
	}
}
