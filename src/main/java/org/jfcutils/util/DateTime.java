package org.jfcutils.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Manage Dates
 * @author celli
 *
 */
public class DateTime {
	
	/**
	 * Get the actual date in the format yyyy/MM/dd HH:mm:ss
	 * @return the actual date
	 */
	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	/**
	 * Get the difference in seconds between two date strings in the format yyyy/MM/dd HH:mm:ss
	 * @param dstart string in the format yyyy/MM/dd HH:mm:ss
	 * @param dend string in the format yyyy/MM/dd HH:mm:ss
	 * @return dend-dstart in seconds, null if the format of the date strings is not correct
	 */
	public static String dateDiffSeconds(String dstart, String dend){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = dateFormat.parse(dstart);
			date2 = dateFormat.parse(dend);
			//in milliseconds
			long diff = date2.getTime() - date1.getTime();
			double diffSeconds = diff / 1000.;
			return String.valueOf(diffSeconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get the difference in minutes between two date strings in the format yyyy/MM/dd HH:mm:ss
	 * @param dstart string in the format yyyy/MM/dd HH:mm:ss
	 * @param dend string in the format yyyy/MM/dd HH:mm:ss
	 * @return dend-dstart in minutes, null if the format of the date strings is not correct
	 */
	public static String dateDiffMinutes(String dstart, String dend){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = dateFormat.parse(dstart);
			date2 = dateFormat.parse(dend);
			//in milliseconds
			long diff = date2.getTime() - date1.getTime();
			double diffMins = (diff / 1000) / 60. ;
			return String.valueOf(diffMins);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get the difference in milliseconds between two date strings in the format yyyy/MM/dd HH:mm:ss
	 * @param dstart string in the format yyyy/MM/dd HH:mm:ss
	 * @param dend string in the format yyyy/MM/dd HH:mm:ss
	 * @return dend-dstart in milliseconds, null if the format of the date strings is not correct
	 */
	public static String dateDiffMillis(String dstart, String dend){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = dateFormat.parse(dstart);
			date2 = dateFormat.parse(dend);
			//in milliseconds
			long diff = date2.getTime() - date1.getTime();
			return String.valueOf(diff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*public static void main(String[] args){
		System.out.println(DateTime.dateDiffSeconds("2015/06/30 10:12:52", "2015/06/30 21:20:33"));
	}*/

}
