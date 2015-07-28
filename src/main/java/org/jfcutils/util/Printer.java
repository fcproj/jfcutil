package org.jfcutils.util;

import java.util.List;
import java.util.Map;

/**
 * This class is responsible to print collections in the standard output
 * @author celli
 *
 */
public class Printer {
	
	/**
	 * Print a general map
	 * @param mapToPrint the map to print
	 */
	public static void printGeneralMap(Map<?, ?> mapToPrint){
		for(Object key: mapToPrint.keySet())
			System.out.println(key + " " + mapToPrint.get(key));
		
	}
	
	/**
	 * Print a general List
	 * @param listToPrint the list to print
	 */
	public static void printGeneralList(List<?> listToPrint){
		for(Object key: listToPrint)
			System.out.println(key);
		
	}
	
	/**
	 * Print a general List of maps
	 * @param list the list to print
	 */
	public static void printGeneralListOfMaps(List<Map<String, String>> list){
		for(Map<String, String> map: list)
			Printer.printGeneralMap(map);
		
	}

}
