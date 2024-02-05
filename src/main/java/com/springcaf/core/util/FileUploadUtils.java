package com.springcaf.core.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.springframework.web.multipart.MultipartFile;

import com.springcaf.core.exception.SpringcafException;

public final class FileUploadUtils {
	
	/**
	 * Save an uploaded file to server
	 * @param file
	 * @param uploadFolder
	 * @param newFileName
	 * @throws IOException
	 */
	public static String uploadFileToWeb(MultipartFile file, String uploadFolder, String newFileName) throws IOException
	{
        byte[] bytes = file.getBytes();
        
        // Creating the upload directory if it doesn't exist
        File dir = new File(uploadFolder);
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        // Create the file on server
        File serverFile = new File(dir.getAbsolutePath()
                + File.separator + newFileName);
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        return serverFile.getAbsoluteFile().toString();
	}

	/**
	 * sftp a file to an FTP server
	 * @param sourceFolder
	 * @param sourceFileName
	 * @param destFolder
	 * @param destFileName
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpUrl
	 * @throws SpringcafException
	 */
    @SuppressWarnings("deprecation")
	public static void sftpFileToServer(String sourceFileFullName, String destFileFullName, String ftpUserName, String ftpPassword, String ftpUrl, int timeout) throws SpringcafException 
    {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        
        try {
			 //check if the file exists
			 File file = new File(sourceFileFullName);
			 if (!file.exists())
			    throw new SpringcafException("Error. Local file not found");
			   
			 //Initializes the file manager
			 manager.init();
			 
			 //Setup our SFTP configuration
			 FileSystemOptions opts = new FileSystemOptions();
			 SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			 SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false);
			 SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, timeout);
			 
			 //Create the SFTP URI using the host name, userid, password,  remote path and file name
			 String sftpUri = "sftp://" + ftpUserName + ":" + ftpPassword +  "@" + ftpUrl + destFileFullName;

			 // Create local file object
			 FileObject localFile = manager.resolveFile(file.getAbsolutePath());
			  
			 // Create remote file object
			 FileObject remoteFile = manager.resolveFile(sftpUri, opts);
			  
			 // Copy local file to sftp server
			 remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
        }
        catch (IOException ioe) {
            throw new SpringcafException(ioe);
        }
        finally {
        	manager.close();
        }
    }
}
