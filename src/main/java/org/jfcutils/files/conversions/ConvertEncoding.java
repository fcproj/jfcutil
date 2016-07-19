package org.jfcutils.files.conversions;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Convert files between various encodings (UTF-8, ASCII...)
 * @author fabrizio celli
 *
 */
public class ConvertEncoding {
	
	/**
	 * Take a UTF8 file and generate an ASCII String
	 * @param filePath the UTF8 filepath
	 * @return the ASCII String of the file
	 * @throws Exception Exception
	 */
	public String convertFileUTF8ToAsici(String filePath) throws Exception{
		/* Read in the utf8 file as a byte array from an input stream */
		FileInputStream fis = new FileInputStream(filePath);
		byte[] utf8Contents = new byte[fis.available()];
		fis.read(utf8Contents);
		fis.close();
		/* Convert the byte array to a utf8 string */
		String utf8String = new String(utf8Contents, "UTF8");
		return convertStringToAscii(utf8String);
	}
	
	/**
	 * Convert a UTF8 String to an ASCII String
	 * @param utf8String the source string
	 * @return the ASCII string
	 */
	public String convertStringToAscii(String utf8String){
		final CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
	    final StringBuilder result = new StringBuilder();
	    for (final Character character : utf8String.toCharArray()) {
	        if (asciiEncoder.canEncode(character)) {
	            result.append(character);
	        } else {
	            result.append("\\u");
	            result.append(Integer.toHexString(0x10000 | character).substring(1).toUpperCase());
	        }
	    }
	    return new String(result);
	}

}
