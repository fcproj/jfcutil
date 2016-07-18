package org.jfcutils.files;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Validate a directory of XML files against a DTD.
 * @author fabrizio celli
 *
 */
public class DTDValidator {
	
	/**
	 * Recursively validate a directory of XML files against a DTD.
	 * 
	 * <br>Example of usage:
	 * <pre>
	 * {@code
	 * ValidateDTD val = new ValidateDTD();
	 * File sourceDir = new File("C:/OUTPUT");
	 * //prepare the parsing
	 * javax.xml.parsers.SAXParserFactory spf = javax.xml.parsers.SAXParserFactory.newInstance();
	 * spf.setValidating(true);
	 * spf.setNamespaceAware(true);
	 * val.validateDir(sourceDir, spf);
	 * }
	 * </pre>
	 * 
	 * @param sourceDir the root directory containing XML files to be validates
	 * @param spf the sax parser
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void validateDir(File sourceDir, SAXParserFactory spf) throws SAXException, ParserConfigurationException {

		//look for XML files in the current directory
		File[] listFiles = sourceDir.listFiles();
		//directories
		Set<File> listDirs = new TreeSet<File>();
		//scan files
		if(listFiles!=null){
			//scan files
			for(File contentFile: listFiles){
				String filename = contentFile.getName().toLowerCase();
				//XML files
				if(filename.contains(".xml")){
					SAXParser saxParser = spf.newSAXParser();
					//DO THE JOB
					XMLReader reader = saxParser.getXMLReader();
					try {
						reader.parse(new InputSource(contentFile.getAbsolutePath()));
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println(filename);
					} catch (SAXException e) {
						e.printStackTrace();
						System.out.println(filename);
					}
				}
				else {
					if(contentFile.isDirectory())
						listDirs.add(contentFile);
				}
			}
		}
		//recursive application
		listFiles = null;
		for(File subDir: listDirs){
			this.validateDir(subDir, spf);
		}	
	}

}
