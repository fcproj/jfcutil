package org.jfcutils.util;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class offers some methods to manage a string
 * @author celli
 *
 */
public class StringUtils {

	/**
	 * Remove beginning whitespaces
	 * @param s the string to clean
	 * @return the string without beginning whitespaces
	 */
	public String trimLeft(String s) {
		return s.replaceAll("^\\s+", "");
	}

	/**
	 * Remove ending whitespaces
	 * @param s the string to clean
	 * @return the string without ending whitespaces
	 */
	public String trimRight(String s) {
		return s.replaceAll("\\s+$", "");
	} 

	/**
	 * Remove special characters from a String
	 * @param source the source string (title, author...)
	 * @return no special characters String
	 */
	public String onlyCharacterString(String source){
		source = source.replaceAll("\\p{Cntrl}", "");
		source = source.replaceAll("\"", "");
		return source;
	}

	/**
	 * Check if a string is an integer
	 * @param input the string to check
	 * @return true if the string is an integer
	 */
	public boolean isInteger(String input) {  
		try {  
			Integer.parseInt(input);  
			return true;  
		}  
		catch(Exception e){  
			return false;  
		}  
	}  

	/**
	 * Check if a string is a characters string
	 * @param input the string to check
	 * @return true if the string is a characters string
	 */
	public boolean isOnlyCharacterString(String input) {  
		String pattern = "[a-zA-Z0-9\\ \\-]+";
		if(Pattern.matches(pattern, input))
			return true;
		return false;
	} 

	/**
	 * Format an integer in a string of n digits. For example 5 with 3 digits will become 005
	 * @param number the source integer
	 * @param digits number of desired digits in the output
	 * @return a string of "digits" digits of the integer "number"
	 */
	public String formatInteger(int number, int digits){
		String format = String.format("%%0%dd", digits);
		return String.format(format, number);
	}

	/**
	 * Format an integer passed as string to separate groups of three digits with spaces.
	 * e.g.: 1000000 becomes 1 000 000
	 * @param number a source integer as string
	 * @return a string that group three digits of a number
	 */
	public String formatNumbersWithSpaces(String number){
		//remove decimal part
		if(number!=null && number.contains("."))
			number = number.substring(0, number.lastIndexOf("."));
		//add spaces every 3 digits
		StringBuffer result = new StringBuffer();
		int counter = 0;
		char[] caratteri = number.toCharArray();
		int numberOfChars = caratteri.length;
		if(numberOfChars>1){
			for(int i = numberOfChars-1; i>=0; i--){
				result.append(caratteri[i]);
				counter++;
				if(counter==3){
					result.append(" ");
					counter=0;
				}
			}
		}
		String numberFormatted = result.reverse().toString();
		if(numberFormatted.startsWith(" "))
			numberFormatted = numberFormatted.substring(1);
		return numberFormatted;
	}

	/**
	 * For stings longer than 1, make the first letter capital
	 * @param s the source string 
	 * @return the string with a capital first letter
	 */
	public String firstCapitalLetter(String s){
		if(s!=null && s.length()>1){
			s = s.toLowerCase();
			s = s.substring(0, 1).toUpperCase() + s.substring(1);
		}
		return s;
	}

	/**
	 * Format a number given as String using a separator.
	 * For example, given the String "1000500000" and the separaator comma ",", returns the String "1,000,500,000"
	 * @param number the number given as a String
	 * @param separator the separator to format the String
	 * @return a number given as String and formatted with the given separator
	 */
	public String formatNumberWithSeparator(String number, String separator){
		double amount = Double.parseDouble(number);
		DecimalFormat formatter = new DecimalFormat("#"+separator+"###");
		return formatter.format(amount);
	}

	/**
	 * Remove non valid XML characters from a string
	 * @param in the input string encoding the XML
	 * @return the string encoding the XML without non valid characters
	 */
	public String stripNonValidXMLCharacters(String in) {
		StringBuffer out = new StringBuffer(); // Used to hold the output.
		char current; // Used to reference the current character.

		if (in == null || ("".equals(in))) return ""; // vacancy test.
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
			if ((current == 0x9) ||
					(current == 0xA) ||
					(current == 0xD) ||
					((current >= 0x20) && (current <= 0xD7FF)) ||
					((current >= 0xE000) && (current <= 0xFFFD)) ||
					((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}   

	/**
	 * Split a string when an uppercase character is found.
	 * @param source the string to split
	 * @return a list of strings computed by splitting this string on uppercase charatcters, null if the source string is null
	 */
	public List<String> splitOnUpperCase(String source){
		if(source!=null){
			List<String> result = new LinkedList<String>();
			String[] splitString = source.split("(?=\\p{Lu})");
			for(String s: splitString)
				result.add(s);
			return result;
		}
		return null;
	}
	
	
}
