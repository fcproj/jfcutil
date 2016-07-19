package org.jfcutils.files.read;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Parse TXT files. Each line contains an entity of the result.
 * 
 * <br>One example of usage:
 * <pre>
 * {@code
 * TXTReader reader = new TXTReader();
 * String sourceFilePath = "C:/Users/celli/Documents/my_doc.txt";
 * Set&lt;String&gt; lines = new HashSet&lt;String&gt;();
 * try {
 * 	lines = reader.parseTxt(sourceFilePath);
 * 	System.out.println("Found "+lines.size()+" lines");
 * } catch (FileNotFoundException e1) {
 * 	e1.printStackTrace();
 * }
 * }
 * </pre>
 * 
 * @author fabrizio celli
 *
 */
public class TXTReader {
	
	/**
	 * Parse a text file of lines. Returns the list of lines.
	 * @param filepath the physical path of the file
	 * @return the mapping of a text file of lines
	 * @throws FileNotFoundException FileNotFoundException
	 */
	public Set<String> parseTxt(String filepath) throws FileNotFoundException{
		Set<String> result = new HashSet<String>();
		//Note that FileReader is used, not File, since File is not Closeable
		File input = new File(filepath);
	    Scanner scanner = new Scanner(new FileReader(input));
	    try {
	      //first use a Scanner to get each line
	      while (scanner.hasNextLine()){
	        String line = scanner.nextLine();
	        if(line!=null && !line.startsWith("#") && line.length()>0){
	        	result.add(line);
	        }
	      }
	    }
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect if the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	    }
		return result;
	}
	
	/**
	 * Parse a text file of lines having a pattern KEY\t|\tVALUE. Returns the mapping.
	 * @param filepath the physical path of the file
	 * @return the mapping of a text file of lines having a pattern KEY\t|\tVALUE
	 * @throws FileNotFoundException FileNotFoundException
	 */
	public Map<String, String> parseTabPipeTxt(String filepath) throws FileNotFoundException{
		Map<String, String> result = new HashMap<String, String>();
		//Note that FileReader is used, not File, since File is not Closeable
	    Scanner scanner = new Scanner(new FileReader(filepath));
	    try {
	      //first use a Scanner to get each line
	      while (scanner.hasNextLine()){
	        String line = scanner.nextLine();
	        if(line!=null && !line.startsWith("#") && line.length()>0){
	        	String[] mapping = line.split("\t\\|\t");
	        	result.put(mapping[0], mapping[1]);
	        }
	      }
	    }
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect if the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	    }
		return result;
	}
	
	/**
	 * Parse a text file of lines having a pattern key=value. Keys are unique, i.e. there aren't two lines with the same key.
	 * Returns the mapping.
	 * @param filepath the physical path of the file
	 * @return the mapping of a text file of lines having a pattern key=value with unique keys
	 * @throws FileNotFoundException FileNotFoundException
	 */
	public Map<String, String> parseEqualTxt(String filepath) throws FileNotFoundException{
		Map<String, String> result = new HashMap<String, String>();
		//Note that FileReader is used, not File, since File is not Closeable
		File input = new File(filepath);
	    Scanner scanner = new Scanner(new FileReader(input));
	    try {
	      //first use a Scanner to get each line
	      while (scanner.hasNextLine()){
	        String line = scanner.nextLine();
	        if(line!=null && !line.startsWith("#") && line.length()>0){
	        	String[] mapping = line.split("=");
	        	result.put(mapping[0], mapping[1]);
	        }
	      }
	    }
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect if the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	    }
		return result;
	}
	
	/**
	 * Parse a text file of lines having a pattern key=value1,value2,value3,...
	 * Keys are unique, i.e. there aren't two lines with the same key.
	 * Returns the mapping.
	 * @param filepath the physical path of the file
	 * @return the mapping of a text file of lines having a pattern key=value1,value2,value3,... with unique keys
	 * @throws FileNotFoundException FileNotFoundException
	 */
	public Map<String, List<String>> parseEqualCommaListValueTxt(String filepath) throws FileNotFoundException{
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		//Note that FileReader is used, not File, since File is not Closeable
		File input = new File(filepath);
	    Scanner scanner = new Scanner(new FileReader(input));
	    try {
	      //first use a Scanner to get each line
	      while (scanner.hasNextLine()){
	        String line = scanner.nextLine();
	        if(line!=null && !line.startsWith("#") && line.length()>0){
	        	String[] mapping = line.split("=");
	        	List<String> tmpList = new LinkedList<String>();
	        	String commaList = mapping[1];
	        	String[] split = commaList.split(",");
	        	for(String s: split)
	        		tmpList.add(s);
	        	result.put(mapping[0], tmpList);
	        }
	      }
	    }
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect if the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	    }
		return result;
	}
	
	/**
	 * Parse a text file of lines having a pattern key=value. The same key can be in more lines.
	 * @param filepath the physical path of the file
	 * @return the mapping of a text file of lines having a pattern key=value, with multiple values for the same key
	 * @throws FileNotFoundException FileNotFoundException
	 */
	public Map<String, List<String>> parseEqualMultipleKeysTxt(String filepath) throws FileNotFoundException{
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		//Note that FileReader is used, not File, since File is not Closeable
	    Scanner scanner = new Scanner(new FileReader(filepath));
	    try {
	      //first use a Scanner to get each line
	      while (scanner.hasNextLine()){
	        String line = scanner.nextLine();
	        if(line!=null && !line.startsWith("#") && line.length()>0){
	        	String[] mapping = line.split("=");
	        	String key = mapping[0];
	        	String value = mapping[1];
	        	if(result.get(key)!=null)
	        		result.get(key).add(value);
	        	else {
	        		List<String> values = new LinkedList<String>();
	        		values.add(value);
	        		result.put(key, values);
	        	}
	        }
	      }
	    }
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect if the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	    }
		return result;
	}
	
	/**
	 * Parse a text file of lines having a pattern key=value. Returns the reverse mapping value=key.
	 * @param filepath the physical path of the file
	 * @return the reverse mapping of a text file of lines having a pattern key=value
	 * @throws FileNotFoundException FileNotFoundException
	 */
	public Map<String, String> reverseParseEqualTxt(String filepath) throws FileNotFoundException{
		Map<String, String> result = new HashMap<String, String>();
		//Note that FileReader is used, not File, since File is not Closeable
		File input = new File(filepath);
	    Scanner scanner = new Scanner(new FileReader(input));
	    try {
	      //first use a Scanner to get each line
	      while (scanner.hasNextLine()){
	        String line = scanner.nextLine();
	        if(line!=null && !line.startsWith("#") && line.length()>0){
	        	String[] mapping = line.split("=");
	        	result.put(mapping[1], mapping[0]);
	        }
	      }
	    }
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect if the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	    }
		return result;
	}
	
	/**
	 * Get next line from a Scanner of a txt file
	 * @param scanner the Scanner to parse lines
	 * @return next line
	 */
	public synchronized String getNextLine(Scanner scanner){
		if (scanner.hasNextLine())
			return scanner.nextLine();
		return null;
	}

}
