package org.jfcutils.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.jfcutils.files.directories.DirectoryManager;
import org.jfcutils.files.write.TXTWriter;
import org.jfcutils.util.StringUtils;

/**
 * This object can split a source text file according to the desired number of lines
 */
public class FileSplitter {

	private final String NEWLINE = System.getProperty("line.separator");

	/**
	 * Split the file, according to the desired number of lines. Writes .txt files.
	 * @param filename the fullpath of the file to be splitted
	 * @throws IOException
	 */
	public void getData(String filename, int lines) throws IOException {
		try {
			//opens the file in a string buffer
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
			StringBuffer stringBuffer = new StringBuffer();
			
			//to manage output paths
			File sourceFile = new File(filename);
			String outputDir = this.createOutputDir(sourceFile, "splitted");
			String sourceFineNameNoSuffix = FilenameUtils.removeExtension(sourceFile.getName());
			
			//writer
			TXTWriter writer = new TXTWriter();

			//performs the splitting
			String line;
			int i = 0;
			int counter = 1;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append(NEWLINE);
				i++;
				if (i >= lines) {
					//saves the lines in the file
					writer.writeStringBuffer(stringBuffer, outputDir + sourceFineNameNoSuffix + "_"+ counter + ".txt");
					//creates a new buffer, so the old can get garbage collected.
					stringBuffer = new StringBuffer();
					i = 0;
					counter++;
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			throw new IOException("Unable to read from file: " + filename);
		}
	}
	
	/*
	 * Creates the output directory, cleaning it if already exists.
	 * Return the directory full path.
	 */
	private String createOutputDir(File sourceFile, String dirName){
		String outPutDir = sourceFile.getParent()+File.separator+dirName+File.separator;
		DirectoryManager dirMan = new DirectoryManager();
		dirMan.checkCreateCleanDir(outPutDir);
		return outPutDir;
		
	}

	/**
	 * 
	 * Reads the parameters [number of lines] [filePathToSplit] and run the splitting
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: Split [number of lines] [filePathToSplit]");
			return;
		}
		
		if(!(new StringUtils()).isInteger(args[0]) || Integer.parseInt(args[0])<0){
			System.err.println("Usage: Split [number of lines] [filePathToSplit]. The first parameter must be a positive Integer.");
			return;
		} 

		String filePath = args[1];
		int lines = Integer.parseInt(args[0]);
		FileSplitter splitter = new FileSplitter();
		
		try {
			splitter.getData(filePath, lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
