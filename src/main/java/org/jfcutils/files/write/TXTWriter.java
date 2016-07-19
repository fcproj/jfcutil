package org.jfcutils.files.write;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Write a txt file
 * @author fabrizio celli
 *
 */
public class TXTWriter {
	
	/**
	 * Append a String in a text file, maintaining the content of the file (if any)
	 * @param content the string to be written
	 * @param filePath the fullpath of the file, together with file name and extension
	 * @param addNewLine if true, the method appends a String and add a \n to the text file
	 * @throws IOException IOException
	 */
	public void appendString(String content, String filePath, boolean addNewLine) throws IOException{
		//file writer
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true));
		out.write(content);
		if(addNewLine)
			out.newLine();
		out.flush();
		out.close();
	}
	
	/**
	 * Append a Collection of String in a text file, maintaining the content of the file (if any)
	 * @param content the Collection to be written
	 * @param filePath the fullpath of the file, together with file name and extension
	 * @param addNewLine if true, the method appends a String and add a \n to the text file
	 * @throws IOException IOException
	 */
	public void appendStrings(Collection<String> content, String filePath, boolean addNewLine) throws IOException{
		//file writer
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true));
		for(String s: content){
			out.write(s);
			out.newLine();
		}
		if(addNewLine)
			out.newLine();
		out.flush();
		out.close();
	}

	/**
	 * Write a String in a text file
	 * @param content the string to be written
	 * @param filePath the fullpath of the file, together with file name and extension
	 * @throws IOException IOException
	 */
	public void writeString(String content, String filePath) throws IOException{
		//file writer
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		out.write(content);
		out.flush();
		out.close();
	}
	
	/**
	 * Write a StringBuffer in a text file 
	 * @param stringBuffer the string buffer
	 * @param filePath the full file name
	 * @throws IOException IOException
	 */
	public void writeStringBuffer(StringBuffer stringBuffer, String filePath) throws IOException {
		this.writeString(stringBuffer.toString(), filePath);
	}
	
	/**
	 * Add a line on a text file already existing. 
	 * @param line the line to add
	 * @param out the buffer
	 * @throws IOException IOException
	 */
	public synchronized void addLine(String line, BufferedWriter out) throws IOException{
		out.write(line);
		out.newLine();
		out.flush();
	}

	/**
	 * Write a Map of String on a text file. Each line is a map entry in the form key=value
	 * Add a timestamp at the beginning #yyyy/MM/dd HH:mm:ss
	 * @param string2string a map of string
	 * @param filePath the fullpath of the file, together with file name and extension
	 * @throws IOException IOException
	 */
	public void writeMapString(Map<String, String> string2string, String filePath) throws IOException{
		//file writer
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		//timestamp
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		out.write("#"+dateFormat.format(date));
		out.newLine();
		for(String title: string2string.keySet()){
			out.write(title);
			out.write("=");
			out.write(string2string.get(title));
			out.newLine();
		}
		out.flush();
		out.close();
	}

	/**
	 * Write a Map of List of Strings on a text file. Each line is a map entry in the form key=value1,value2,...
	 * Add a timestamp at the beginning #yyyy/MM/dd HH:mm:ss
	 * @param string2list a map of List of Strings
	 * @param filePath the fullpath of the file, together with file name and extension
	 * @throws IOException IOException
	 */
	public void writeMapListString(Map<String, List<String>> string2list, String filePath) throws IOException{
		//file writer
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		//timestamp
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		out.write("#"+dateFormat.format(date));
		out.newLine();
		for(String key: string2list.keySet()){
			out.write(key+"=");
			List<String> tmp = string2list.get(key);
			String concat = "";
			for(String value: tmp)
				concat = concat + value + ",";
			if(concat.endsWith(","))
				concat = concat.substring(0, concat.length()-1);
			out.write(concat);
			out.newLine();
		}
		out.flush();
		out.close();
	}

	/**
	 * Add a Map of List of Strings on a text file already existing. Each line is a map entry in the form key=value1,value2,...
	 * @param string2list a map of List of Strings
	 * @param out the buffer
	 * @throws IOException IOException
	 */
	public synchronized void addMapListString(Map<String, List<String>> string2list, BufferedWriter out) throws IOException{
		for(String key: string2list.keySet()){
			out.write(key+"=");
			List<String> tmp = string2list.get(key);
			String concat = "";
			for(String value: tmp)
				concat = concat + value + ",";
			if(concat.endsWith(","))
				concat = concat.substring(0, concat.length()-1);
			out.write(concat);
			out.newLine();
		}
		out.flush();
	}

	/**
	 * Write a set of lines
	 * Add a timestamp at the beginning #yyyy/MM/dd HH:mm:ss
	 * @param lines a set of string
	 * @param filePath the fullpath of the file, together with file name and extension
	 * @throws IOException IOException
	 */
	public void writeLines(Collection<String> lines, String filePath) throws IOException{
		//file writer
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		//timestamp
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		out.write("#"+dateFormat.format(date));
		out.newLine();
		for(String line: lines){
			out.write(line);
			out.newLine();
		}
		out.flush();
		out.close();
	}
	
	/**
	 * Add a list of lines on a text file already existing. 
	 * @param lines the list of lines to add
	 * @param out the buffer
	 * @throws IOException IOException
	 */
	public synchronized void addLines(Collection<String> lines, BufferedWriter out) throws IOException{
		for(String line: lines){
			out.write(line);
			out.newLine();
		}
		out.flush();
	}


}
