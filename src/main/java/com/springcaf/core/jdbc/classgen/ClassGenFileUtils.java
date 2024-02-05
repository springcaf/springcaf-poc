package com.springcaf.core.jdbc.classgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.springcaf.core.exception.SpringcafException;

final class ClassGenFileUtils {
	
	/**
	 * Generate a Java class into the source tree
	 * @param sourceBaseLocation
	 * @param packageName
	 * @param className
	 * @param classContent
	 * @param replaceExisting
	 * @throws IOException
	 * @throws SpringcafException
	 */
	public static String createJavaClass(String sourceBaseLocation, String packageName, String className, String classContent, boolean replaceExisting) throws IOException, SpringcafException
	{
		String classFolderName = createFolderFromPackageName(sourceBaseLocation, packageName);
		String classFileName = classFolderName + File.separator + className + ".java";
		writeToFile(classFileName, classContent, replaceExisting);
		
		return classFileName;
	}

	/**
	 * Create folder structure from a package name
	 * @param baseFolder
	 * @param packageName
	 * @return
	 * @throws SpringcafException
	 */
	private static String createFolderFromPackageName(String baseFolder, String packageName) throws SpringcafException
	{
		// parse and build the package structure
		String[] packageParts = packageName.split("\\.");
		
		return createFolders(baseFolder, packageParts);
	}
	
	/**
	 * Create a chain of folders down from the base folder
	 * @param baseFolder
	 * @param downPaths
	 * @throws SpringcafException 
	 */
	private static String createFolders(String baseFolder, String[] downPaths) throws SpringcafException
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
	 * Write content to a file
	 * @param fileName
	 * @param fileContent
	 * @param replaceExisting
	 * @throws IOException
	 */
	private static void writeToFile(String fullFileName, String fileContent, boolean replaceExisting) throws IOException
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
	
}
