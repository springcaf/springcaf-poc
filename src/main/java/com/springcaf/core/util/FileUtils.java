package com.springcaf.core.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

import com.springcaf.core.exception.SpringcafException;

public final class FileUtils {

	/**
	 * Write content to a file
	 * @param fileName
	 * @param fileContent
	 * @param replaceExisting
	 * @throws IOException
	 */
	public static void writeToFile(String fullFileName, String fileContent, boolean replaceExisting) throws IOException
	{
		File tmp = new File(fullFileName);
		if(tmp.exists() && !replaceExisting)
		{
			return;
		}
		
		// write the content to file
		FileWriter fw = new FileWriter(fullFileName);
		fw.write(fileContent);
		fw.close();
	}
	
	/**
	 * Read the whole file into Java String
	 * @param fullFileName
	 * @return
	 * @throws IOException
	 */
	public static String readFromFile(String fullFileName) throws IOException
	{
		FileReader fr = new FileReader(fullFileName);
		LineNumberReader lnr = new LineNumberReader(fr);
		StringBuffer buffer = new StringBuffer();
		
		String s = null;
		while((s=lnr.readLine()) != null)
		{
			buffer.append(s + "\r\n");
		}
		
		lnr.close();
		fr.close();
		
		return buffer.toString();
	}
	

	
	/**
	 * Read first line from a file
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String getFirstLineFromFile(String fullFileName) throws IOException
	{
		FileReader fr = new FileReader(fullFileName);
		LineNumberReader lnr = new LineNumberReader(fr);
		String line = null;
		line = lnr.readLine();
		
		lnr.close();
		fr.close();
		
		return line;
	}
	
	/**
	 * Create a folder in file system for a given folderName
	 * @param folderName
	 */
	public static void createFolder(String folderName)
	{
		File tmp = new File(folderName);
		if(!tmp.exists())
		{
			tmp.mkdir();
		}
	}

	/**
	 * Create a chain of folders down from the base folder
	 * @param baseFolder
	 * @param downPaths
	 * @throws SpringcafException 
	 */
	public static String createFolders(String baseFolder, String[] downPaths) throws SpringcafException
	{
		String tmpFilePath = baseFolder;
		File tmpFile = new File(tmpFilePath);
		if(!tmpFile.exists())
		{
			throw new SpringcafException("Invalid base folder name");
		}
		
		// if there is no package name
		if(downPaths == null)
		{
			return baseFolder;
		}
		
		// parse and build the downPaths structure
		for(String downPath : downPaths)
		{
			tmpFilePath += File.separator + downPath;
			tmpFile = new File(tmpFilePath);
			if(!tmpFile.exists())
			{
				tmpFile.mkdir();
			}
		}
		
		return tmpFilePath;
	}

	/**
	 * Get file line count
	 * @param fileName
	 * @param containsHeader
	 * @return
	 * @throws IOException
	 */
	public static int getFileLineCount(String fileName, boolean containsHeader) throws IOException
	{
		FileReader fr = new FileReader(fileName);
		LineNumberReader lnr = new LineNumberReader(fr);
		int counter = 0;
		
		@SuppressWarnings("unused")
		String s = null;
		while((s=lnr.readLine()) != null)
		{
			counter++;
		}
		
		if(containsHeader)
		{
			counter--;
		}
		
		lnr.close();
		fr.close();
		
		return counter;
	}
	
}
