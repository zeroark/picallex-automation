package zeroark.picallex;

import java.util.*;
import java.io.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import zeroark.picallex.entities.*;

/**
 * Main application class
 * This will wrap up all the application logic in one single place!
 */
public class App 
{
	// Getting our logger so we can start writing to the file
	private static Logger logger = LogManager.getLogger();
	
    public static void main( String[] args )
    {       	    	
    	try
    	{   
    		Entry entry = null;
    		
    		// Path of the file we want to open goes here. (Create a "data" folder on the project and add your test files there)
    		String filename = "data/BANCO FALABELLA.xls";
    		
    		// Path of the resulting file, after processing 
    		String resultname = "data/result.csv";
    		
    		// Getting the source file and opening it
    		File file = new File(filename);
    		FileInputStream fIP = new FileInputStream(file);
    		
    		// Getting the result file and opening it
    		File resultFile = new File(resultname);
    		FileInputStream rfIP = new FileInputStream(file);    		
    		
    		// Making sure the file exists before doing anything.
    		if(file.isFile() && file.exists())
    	    {
    	    	//System.out.println("openworkbook.xlsx file open successfully.");
    			logger.info(String.format("%s file open successfully.", filename));
    	    }
    	    else
    	    {
    	    	//System.out.println("Error to open openworkbook.xlsx file.");
    	    	logger.info(String.format("Error to open %s file.", filename));
    	    }
    		
    		// Making sure the result file exists before doing anything.
    		if(resultFile.isFile() && resultFile.exists())
    	    {
    			resultFile.delete();
    	    }
    		
    		// Starting the File Writer to generate the resulting CSV file
    		FileWriter filewriter = new FileWriter(resultname);
    		filewriter.append("Telefono,Nombre,Notas\n");    		
    		
    		// Open the file as an Excel Workbook
    		// TODO: Works on XLS for now only. Need to centralize logic to work for both XLS and XLSX.
    		HSSFWorkbook  workbook = new HSSFWorkbook (fIP);    	    
    	    
    	    //Get first sheet from the workbook
    	    HSSFSheet sheet = workbook.getSheetAt(0);
    	    
    	    // Start to iterate through all the rows!
    	    Iterator<Row> rowIterator = sheet.rowIterator();
    	    
    	    // Do this while there's a next row, else just finish
    	    while(rowIterator.hasNext())
    	    {
    	    	List<Entry> entries = new ArrayList<Entry>();    	    	
    	    	
    	    	// Getting the next row so we can continue reading
    	    	Row row = rowIterator.next();    	    	
    	    	int rowNumber = row.getRowNum();
    	    	logger.info(String.format("Currently Processing Row #%2d", rowNumber));
    	    	
    	    	if(rowNumber == 28938) 
    	    	{
    	    		String asd = "123";
    	    	}
    	    	
    	    	if(rowNumber == 0) continue;
    	    	
    	    	// Start to iterate through all the row's cells
    	    	Iterator<Cell> cellIterator = row.cellIterator();
    	    	
    	    	entry = new Entry(); 
    	    	String fullName = "";
    	    	
    	    	// Do this while there's a next cell, else just finish
    	    	while(cellIterator.hasNext())
    	    	{    	    		   	    	    	    	
    	    		// Get the next cell in our row, if any
    	    		Cell cell = cellIterator.next();
    	    		int columnIndex = cell.getColumnIndex();
    	    		//logger.info(String.format("Currently Processing Cell #%2d", cell.getColumnIndex()));    	    		

    	    		// Getting the cell type, so we can use the correct method to print! (Commented out, since we don't need it anymore)
//    	    		switch(cell.getCellType())
//    	    		{
//    	    			case Cell.CELL_TYPE_STRING:
//    	    				//System.out.println(cell.getStringCellValue());
//    	    				logger.debug(cell.getStringCellValue());
//    	    				break;
//    	    			case Cell.CELL_TYPE_NUMERIC:
//    	    				//System.out.println(cell.getNumericCellValue());
//    	    				logger.debug(cell.getNumericCellValue());
//    	    				break;
//    	    			default:
//    	    				//System.out.println(cell.getStringCellValue());
//    	    				logger.debug(cell.getStringCellValue());
//    	    				break;
//    	    		}
    	    		
    	    		switch(columnIndex)
    	    		{
    	    			case 0:
    	    				fullName += cell.getStringCellValue();
    	    				break;
    	    			case 1:
    	    				fullName += " " + cell.getStringCellValue();
    	    				break;
    	    			case 2:
    	    				fullName += " " + cell.getStringCellValue();
    	    				break;
    	    			case 3:
    	    				entry.Name = fullName;
    	    				break;
    	    			case 6:
    	    				
    	    				entry.Phone = Integer.toString(Double.valueOf(cell.getNumericCellValue()).intValue());
    	    			default:
    	    				break;
    	    		}    	    	    	    	
    	    	}
    	    	
    	    	filewriter.append(entry.toString());
    	    	//logger.info(entry.toString());
	    		entries.add(entry);
	    		
	    		if(!entry.Name.matches("^[\\p{L} .'-]+$")) logger.fatal(String.format("Row %2d has issues with the name! (Name: %s)", rowNumber, entry.Name));	    				
    	    }
    	    
    	    // Finalizing the file writer and saving the file
    	    filewriter.flush();
    		filewriter.close();
    
    	}
    	catch(Exception ex)
    	{
    		// If something gets wrong, write the message
    		//System.out.println(ex.getMessage());
    		logger.catching(ex);
    	}
    }
}
