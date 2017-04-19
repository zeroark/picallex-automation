package zeroark.picallex;

import java.util.*;
import java.io.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zeroark.picallex.entities.*;
import zeroark.picallex.helpers.FileHelper;

/**
 * Main application class
 * This will wrap up all the application logic in one single place!
 */
public class App 
{ 				
	// Logger we will use for this class logging
	private final static Logger logger = LogManager.getLogger();	
	
	// Relative path of the file we want to read
	private static final String filename = "data/BANCO FALABELLA.xls";
	
	// Relative path of the resulting CSV file 
	private static final String resultname = "data/result.csv";
	
	@Autowired
	private static FileHelper fileHelper;
	
	public static void InitializeApplication(){
		// Get the application context from the configuration file
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		
		// Setup the beans that we'll use for our process here
		fileHelper = (FileHelper) context.getBean("fileHelper");
	}	
	
    public static void main( String[] args )
    {   
    	Entry entry = null;
		List<Entry> entries = null;
    	
    	try
    	{
    		// Initialize our application context and beans.
    		InitializeApplication();    	    	    		    	    		    		    		    		    		  		    
    		
    		// If source file path is invalid, finish the process
    		if(!fileHelper.IsValidFile(filename)){
    			logger.info(String.format("Error to open %s file.", filename));
    			return;
    		}
    		
    		// If result file path exists, delete it
    		if(fileHelper.IsValidFile(resultname)){
    			File resultFile = new File(resultname);
    			resultFile.delete();
    		}    	    	
    		
    		// Open the source file and start reading it.
    		File file = new File(filename);
    		FileInputStream fIP = new FileInputStream(file);
    		
    		// Open the file as an Excel Workbook
    		// TODO: Works on XLS for now only. Need to centralize logic to work for both XLS and XLSX.    		
    		HSSFWorkbook  workbook = new HSSFWorkbook (fIP);    	    
    	    
    	    //Get first sheet from the workbook
    	    HSSFSheet sheet = workbook.getSheetAt(0);
    	    
    	    // Start to iterate through all the rows!
    	    Iterator<Row> rowIterator = sheet.rowIterator();
    	    
    	    entries = new ArrayList<Entry>();
    	    
    	    // Do this while there's a next row, else just finish
    	    while(rowIterator.hasNext())
    	    {    	    	    	        	    	
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
    	    	    	    	
	    		entries.add(entry);
	    		
	    		if(!entry.Name.matches("^[\\p{L} .'-]+$")) logger.fatal(String.format("Row %2d has issues with the name! (Name: %s)", rowNumber, entry.Name));	    				
    	    }
    	    
    	    fileHelper.ExportEntriesToCSV(entries, resultname);
    
    	}
    	catch(Exception ex)
    	{
    		fileHelper.ExportEntriesToCSV(entries, resultname);
    		
    		// If something gets wrong, write the message
    		//System.out.println(ex.getMessage());    		
    		logger.catching(ex);
    	}
    }
}
