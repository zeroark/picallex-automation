package zeroark.picallex;

import java.io.*;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

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
    		// Path of the file we want to open goes here. (Create a "data" folder on the project and add your test files there)
    		String filename = "data/BANCO FALABELLA.xls";
    		
    		// Getting the file on the file system and opening it.
    		File file = new File(filename);
    		FileInputStream fIP = new FileInputStream(file);
    		
    		// Making sure the file exists before doing anything.
    		if(file.isFile() && file.exists())
    	    {
    	    	System.out.println("openworkbook.xlsx file open successfully.");
    	    }
    	    else
    	    {
    	    	System.out.println("Error to open openworkbook.xlsx file.");
    	    }
    		
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
    	    	// Getting the next row so we can continue reading
    	    	Row row = rowIterator.next();    	    	
    	    	
    	    	// Start to iterate through all the row's cells
    	    	Iterator<Cell> cellIterator = row.cellIterator();
    	    	
    	    	// Do this while there's a next cell, else just finish
    	    	while(cellIterator.hasNext())
    	    	{
    	    		// Get the next cell in our row, if any
    	    		Cell cell = cellIterator.next();

    	    		// Getting the cell type, so we can use the correct method to print! 
    	    		switch(cell.getCellType())
    	    		{
    	    			case Cell.CELL_TYPE_STRING:
    	    				//System.out.println(cell.getStringCellValue());
    	    				logger.info(cell.getStringCellValue());
    	    				break;
    	    			case Cell.CELL_TYPE_NUMERIC:
    	    				//System.out.println(cell.getNumericCellValue());
    	    				logger.info(cell.getNumericCellValue());
    	    				break;
    	    			default:
    	    				//System.out.println(cell.getStringCellValue());
    	    				logger.info(cell.getStringCellValue());
    	    				break;
    	    		}
    	    	}
    	    }

    
    	}
    	catch(Exception ex)
    	{
    		// If something gets wrong, write the message
    		//System.out.println(ex.getMessage());
    		logger.catching(ex);
    	}
    }
}
