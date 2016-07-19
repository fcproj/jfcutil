package org.jfcutils.files.read;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Print BufferReaders in Strings or Standard Output
 * @author fabrizio celli
 *
 */
public class BufferManager {
	
	/**
	 * Print the content of a buffer in the standard output
	 * @param inMessage the bufferReader
	 * @throws IOException IOException
	 */
	public void printBufferedReader(BufferedReader inMessage) throws IOException{
		String strLine;
		if(inMessage!=null){
			while ((strLine = inMessage.readLine()) != null) {
				System.out.println(strLine);
			}
			inMessage.close();
		}
	}

	/**
	 * Print the content of a buffer in a String
	 * @param inMessage the bufferReader
	 * @return the String containing the content of the BufferReader
	 * @throws IOException IOException
	 */
	public String readBufferedReader(BufferedReader inMessage) throws IOException{
		String strLine;
		String output = ""; 
		if(inMessage!=null){
			while ((strLine = inMessage.readLine()) != null) {
				output = output + strLine +"\n";
			}
			inMessage.close();
			return output;
		}
		else
			return null;
	}

}
