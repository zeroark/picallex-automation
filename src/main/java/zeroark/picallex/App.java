package zeroark.picallex;

import java.io.*;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try
    	{
    		// Path of the file we want to open goes here
    		String filename = "data/BANCO FALABELLA.xls";
    		
    		// Opening the file
    		File file = new File(filename);
    		FileInputStream fIP = new FileInputStream(file);
    		
    		//Get the workbook instance for XLSX file    		
    		HSSFWorkbook  workbook = new HSSFWorkbook (fIP);

    	    if(file.isFile() && file.exists())
    	    {
    	    	System.out.println("openworkbook.xlsx file open successfully.");
    	    }
    	    else
    	    {
    	    	System.out.println("Error to open openworkbook.xlsx file.");
    	    }
    	    
    	    //Get first sheet from the workbook
    	    HSSFSheet sheet = workbook.getSheetAt(0);
    	    
    	    Iterator<Row> rowIterator = sheet.rowIterator();
    	    
    	    while(rowIterator.hasNext())
    	    {
    	    	Row row = rowIterator.next();    	    	
    	    	Iterator<Cell> cellIterator = row.cellIterator();
    	    	
    	    	while(cellIterator.hasNext())
    	    	{
    	    		Cell cell = cellIterator.next();

    	    		switch(cell.getCellType())
    	    		{
    	    			case Cell.CELL_TYPE_STRING:
    	    				System.out.println(cell.getStringCellValue());
    	    				break;
    	    			case Cell.CELL_TYPE_NUMERIC:
    	    				System.out.println(cell.getNumericCellValue());
    	    				break;
    	    			default:
    	    				System.out.println(cell.getStringCellValue());
    	    				break;
    	    		}
    	    	}
    	    }

    
    	}
    	catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    }
}
