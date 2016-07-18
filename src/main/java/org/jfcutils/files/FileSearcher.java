package org.jfcutils.files;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

/**
 * This object recursively searches for a file in a directory.
 *
 * @author fabrizio celli
 *
 */
public class FileSearcher {
	
	/**
	 * Recursively search a file in a directory given the filename.
	 * <br>Example:
	 * <pre>
	 * {@code
	 * SearchFile searcher = new SearchFile();
	 * File f = searcher.searchFile(".", "index.html");
	 * System.out.println(f.getAbsolutePath());.
	 * }
	 * </pre>
	 * 
	 * @param rootS the root directory to start the search
	 * @param fileName the name of the file to search
	 * @return the found File, null if the file is not in
	 */
	public File searchFile(String rootS, String fileName){
		File root = new File(rootS);
		if(root!=null && root.exists() && root.isDirectory() && root.canRead()){
			try {
				boolean recursive = true;

				Collection<?> files = FileUtils.listFiles(root, null, recursive);

				for (Iterator<?> iterator = files.iterator(); iterator.hasNext();) {
					File file = (File) iterator.next();
					if (!file.isDirectory() && file.getName().equalsIgnoreCase(fileName))
						return file;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
