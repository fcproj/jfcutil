package org.jfcutils.files.conversions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * This class converts files (HTML, PDF..) to TXT
 * @author celli
 *
 */
public class ConvertToTXT {

	/**
	 * Remove HTML tags from the file content
	 * @param sourceFilePath the path of the source file
	 * @param outputFilePath the path of the output file, after cleaning HTML tags
	 * @param deleteSource if true, delete the source file
	 */
	public void removeHTMLTags(String sourceFilePath, String outputFilePath, boolean deleteSource){
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			//read the file in a string and remove HTML tags
			br = new BufferedReader(new FileReader(sourceFilePath));
			String line;
			while ( (line=br.readLine()) != null) {
				sb.append(line);
			}
			String nohtml = sb.toString().replaceAll("\\<.*?>","");

			//delete the input file
			if(deleteSource){
				File input = new File(sourceFilePath);
				input.delete();
			}

			//create the new file
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFilePath));
			out.write(nohtml);
			out.close();
		} catch(IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(br!=null)
					br.close();
			} catch(Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}	

	/**
	 * Covert a PDF to a TXT file and remove the PDF. Can limit the number of pages.
	 * @param filePath the full path of the PDF file
	 * @param maxPageNumber maximun number of pages to convert, 0 to remove limits
	 */
	public void convertPdfToTxt(String filePath, int maxPageNumber){
		//the pdf file
		File input = new File(filePath);

		// The text file where you are going to store the extracted data
		filePath = filePath.replace(".pdf", ".txt");
		File output = new File(filePath); 
		PDDocument pd = null;
		PDFTextStripper stripper;
		BufferedWriter wr = null;
		try {
			pd = PDDocument.load(input);
			stripper = new PDFTextStripper();
			//page limits
			if(maxPageNumber>0 && pd.getNumberOfPages()>maxPageNumber)
				stripper.setEndPage(maxPageNumber);
			wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
			stripper.writeText(pd, wr);
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally {
			/*
			 * Close streams
			 */
			if (pd != null) {
				try {
					pd.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
			//I use close() to flush the stream.
			try {
				if(wr!=null)
					wr.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}	

}
