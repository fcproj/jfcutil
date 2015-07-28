package org.jfcutils.files.directories;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * Manage the creation of a directory
 * @author celli
 *
 */
public class DirectoryManager {

	/**
	 * Check if the directory exists. If it exists, cleans the content. Otherwise, creates the directory.
	 * @param directoryName the full path of the directory
	 * @return true if the directory exists and is empty after the method execution
	 */
	public boolean checkCreateCleanDir(String directoryName){
		File theDir = new File(directoryName);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			try{
				theDir.mkdir();
			} catch(SecurityException se){
				//handle it
				System.out.println("ERROR: You don't have permissions to write the directory: " + directoryName);
				return false;
			}      
		} else {
			//clean the content
			try{
				FileUtils.cleanDirectory(theDir); 
			} catch(IOException se){
				//handle it
				System.out.println("ERROR: Can't clean the directory: " + directoryName);
				return false;
			} 
		}
		
		return true;
	}
	
	/**
	 * Recursively delete the directory and all its content (files and nested directories)
	 * @param path the full path of the directory to delete
	 * @return true, if the directory have been correctly deleted
	 */
	public boolean deleteDirectory(File path) {
		if(path.exists()) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}
				else {
					files[i].delete();
				}
			}
		}
		return(path.delete());
	}

}
