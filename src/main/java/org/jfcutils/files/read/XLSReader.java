package org.jfcutils.files.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jfcutils.util.StringUtils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

/**
 * Read a file Excel with two columns
 * Requires the library jxl.jar http://jexcelapi.sourceforge.net/
 * @author fabrizio celli
 *
 */
public class XLSReader {
	
	/**
	 * Read a file Excel with one column
	 * @param fileInputStream the file excel
	 * @return the set of lines as strings
	 */
	public Set<String> readColumn(InputStream fileInputStream){
		Set<String> data = new TreeSet<String>();
		WorkbookSettings ws = null;
		Workbook workbook = null;
		Sheet s = null;
		Cell rowData[] = null;
		int rowCount = 0;

		try {
			ws = new WorkbookSettings();
			ws.setEncoding("ISO-8859-1");
			workbook = Workbook.getWorkbook(fileInputStream, ws);

			//Getting Default Sheet 0
			s = workbook.getSheet(0);

			//Total No Of Rows in Sheet, will return you no of rows that are occupied with some data
			rowCount = s.getRows();

			//Reading Individual Row Content, not the header
			for (int i = 1; i < rowCount; i++) {
				//Get Individual Row
				rowData = s.getRow(i);
				String content = rowData[0].getContents();
				data.add(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		
		return data;
	}

	/**
	 * Read a file Excel with two columns. The first column must contain a value, else discard the row.
	 * @param fileInputStream the file excel
	 * @return the map of lines as strings
	 */
	public Map<String, String> readTwoColumns(InputStream fileInputStream){
		Map<String, String> data = new HashMap<String, String>();
		WorkbookSettings ws = null;
		Workbook workbook = null;
		Sheet s = null;
		Cell rowData[] = null;
		int rowCount = 0;
		
		StringUtils cleaner = new StringUtils();

		try {
			ws = new WorkbookSettings();
			ws.setEncoding("ISO-8859-1");
			workbook = Workbook.getWorkbook(fileInputStream, ws);

			//Getting Default Sheet 0
			s = workbook.getSheet(0);

			//Total No Of Rows in Sheet, will return you no of rows that are occupied with some data
			rowCount = s.getRows();

			//Reading Individual Row Content and the header
			for (int i = 0; i < rowCount; i++) {
				//Get Individual Row
				rowData = s.getRow(i);
				String content0 = rowData[0].getContents();
				if(content0!=null && !content0.trim().equals("")){
					content0 = cleaner.trimRight(content0);
					content0 = cleaner.trimLeft(content0);
					String content1 = rowData[1].getContents();
					content1 = cleaner.trimRight(content1);
					content1 = cleaner.trimLeft(content1);
					data.put(content0, content1);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}

		return data;
	}

}
