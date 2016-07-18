package org.jfcutils.files.directories;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/**
 * Utilities to decompress (e.g. zip/unzip) files and directories
 * @author Fabrizio Celli
 *
 */
public class Decompressor {

	/**
	 * List zip content
	 * @param zipName the fullpath of the zip file
	 * @return an array of strings listing the zip content
	 */
	public ArrayList<String> getZipContent(String zipName){
		//result
		ArrayList<String> res = new ArrayList<String>();

		try {	
			// Open the ZIP file
			@SuppressWarnings("resource")
			ZipFile zf = new ZipFile(zipName);
			// Enumerate each entry
			for (@SuppressWarnings("rawtypes")
			Enumeration entries = zf.entries(); entries.hasMoreElements();) {
				// Get the entry name
				String zipEntryName = ((ZipEntry)entries.nextElement()).getName();
				res.add(zipEntryName);
			}
		} catch (IOException e) {}
		return res;
	}

	/**
	 * Unzip a zip file, with all its subdirectories and content files
	 * @param zipFullPathName the fullpath of the zip file to be extracted
	 * @param outputDirName the name of the directory containing the output, use null or empty string to extract at the same level of the zip
	 * @return the output directory path
	 */
	public String unzipFile(String zipFullPathName, String outputDirName) {
		@SuppressWarnings("rawtypes")
		Enumeration entries;
		ZipFile zipFile;

		//extract path and create the TEMP directory
		String prefix = "";
		int lastPath = zipFullPathName.lastIndexOf("\\");
		if(lastPath==-1)
			lastPath = zipFullPathName.lastIndexOf("/");
		if(lastPath!=-1)
			prefix = zipFullPathName.substring(0, lastPath); 
		prefix = prefix + "/";

		//check output dir
		if(outputDirName!=null && outputDirName.length()>0){
			prefix = prefix + outputDirName + "/";
			(new File(prefix)).mkdir();
		}

		//start extraction
		try {
			zipFile = new ZipFile(zipFullPathName);
			entries = zipFile.entries();

			while(entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry)entries.nextElement();
				if(entry.isDirectory()) {
					// Assume directories are stored parents first then children.
					//System.err.println("Extracting directory: " + entry.getName());
					// This is not robust, just for demonstration purposes.
					(new File(prefix+entry.getName())).mkdir();
					continue;
				}
				FileOutputStream fos = new FileOutputStream(prefix+entry.getName());
				copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(fos));
			}
			zipFile.close();
		} catch (IOException ioe) {
			//System.err.println("Unhandled exception:");
			ioe.printStackTrace();		
		}
		return prefix;
	}

	private final void copyInputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);
		in.close();
		out.flush();
		out.close();
	}

	/**
	 * Unzip all zip files in a given directory. Zip files should be at the same level, not nested in other directories.
	 * If the boolean is tue, the content of each Zip files is extracted in a directory with the same name of the Zip file, at the same level of the given directory.
	 * If the boolean is false, the content of each Zip files is extracted in the given directory.
	 * @param dirPath the given directory, containing zip files
	 * @param createNewDir true to create a directory for each Zip file to extract
	 * @return true if in the given directory all zip files have been extracted
	 */
	public boolean unzipDirWithManyFiles(String dirPath, boolean createNewDir){
		Decompressor util = new Decompressor();
		//search for zip files in the directory
		File dir = new File(dirPath);
		if(dir.exists()){
			File[] listFiles = dir.listFiles();
			for(File contentFile: listFiles){
				if(contentFile.getName().toLowerCase().contains(".zip")){
					//extract first level zip files, in a directory with the same name
					String filePath = contentFile.getAbsolutePath();
					if(createNewDir)
						util.unzipFile(filePath, filePath.replace(".zip", ""));
					else
						util.unzipFile(filePath, "");
				}
			}
			return true;
		}
		else
			return false;
	}

	/**
	 * Uncompress a tar.gz file.
	 * Example: untarFile("/output/201408010258_RDF.tar.gz", "/output/", false);
	 * @param tarFullPathName the fullpath to the source tar.gz file
	 * @param outputDirName the destination directory
	 * @param flatAllContent if true, all extracted files will be in the same directory, flatting the original structure
	 */
	public void untarFile(String tarFullPathName, String outputDirName, boolean flatAllContent) {
		File tarFile = new File(tarFullPathName);
		//check if the source tar.gz file exists
		if(tarFile.exists()){
			File dest = new File(outputDirName);
			this.untarFile(tarFile, dest, flatAllContent);
		}
		else {
			System.out.println(tarFullPathName+" does not exist.");
		}
	}

	/**
	 * Uncompress a tar.gz file
	 * @param tarFile the File to untar
	 * @param dest the File of destination directory
	 * @param flatAllContent if true, all extracted files will be in the same directory, flatting the original structure
	 */
	public void untarFile(File tarFile, File dest, boolean flatAllContent) {
		// creates the destination directory
		if(!dest.exists())
			dest.mkdir();
		//start the extraction
		TarArchiveInputStream tarIn = null;
		try {
			tarIn = new TarArchiveInputStream(new GzipCompressorInputStream(
					new BufferedInputStream(new FileInputStream(tarFile))));
			TarArchiveEntry tarEntry = tarIn.getNextTarEntry();

			// scan all the entry in the TAR file
			while (tarEntry != null) {
				File destPath = new File(dest, tarEntry.getName());
				//creates the structure only if flatAllContent is false
				if (tarEntry.isDirectory() && !destPath.exists() &&!flatAllContent) {
					destPath.mkdirs();
				} else 
					//manage a file in the archive
					if (!tarEntry.isDirectory()){
						//flat the content
						if(flatAllContent)
							destPath = new File(dest, destPath.getName());
						//creates the output file
						destPath.createNewFile();
						byte [] btoRead = new byte[1024];
						BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(destPath));

						int len = 0;
						while((len = tarIn.read(btoRead)) != -1) {
							bout.write(btoRead,0,len);
						}
						bout.flush();
						bout.close();
						btoRead = null;
					}
				tarEntry = tarIn.getNextTarEntry();
			}
			tarIn.close();
		} catch(IOException e){
			e.printStackTrace();		
		}
	} 
	
	/**
	 * Untar all tar.gz files in the given directory. The option flatAllContent allows to flat the structures of the files, decompressing all files in the same directory.
	 * Example: untarDirWithManyFiles("/output/", "/output/tar", true);
	 * @param dirPath the given directory, containing tar.gz files
	 * @param destPath the output directory
	 * @param flatAllContent if true, all extracted files will be in the same directory, flatting the original structure
	 * @return if in the given directory all TAR files have been extracted
	 */
	public boolean untarDirWithManyFiles(String dirPath, String destPath, boolean flatAllContent){
		Decompressor util = new Decompressor();
		//search for zip files in the directory
		File dir = new File(dirPath);
		if(dir.exists()){
			File[] listFiles = dir.listFiles();
			for(File contentFile: listFiles){
				if(contentFile.getName().toLowerCase().contains(".tar.gz")){
					//extract first level zip files, in a directory with the same name
					String filePath = contentFile.getAbsolutePath();
					util.untarFile(filePath, destPath, flatAllContent);
				}
			}
			return true;
		}
		else
			return false;
	}

	public static void main(String[] args){
		Decompressor util = new Decompressor();
		//System.out.println(util.getZipContent(path));
		//util.unzipFile("C:/Users/celli/Desktop/Test/tmp/201406250317_RDF.zip", "");
		util.untarDirWithManyFiles("C:/Users/celli/Desktop/output2/", "C:/Users/celli/Desktop/output2/", true);
	}

}
