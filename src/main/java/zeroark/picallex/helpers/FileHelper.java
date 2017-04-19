package zeroark.picallex.helpers;

import java.io.*;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zeroark.picallex.entities.Entry;

public class FileHelper {
	
	private final static Logger logger = LogManager.getLogger();
	
	private final String CSV_FILE_HEADER = "Telefono,Nombre,Notas\n";
	
	public boolean IsValidFile(String path){
		File file = new File(path);
		return file.exists() && file.isFile();
	}
	
	public void ExportEntriesToCSV(List<Entry> entries, String path){
		try{
			// Initialize the CSV file with our header
			FileWriter fileWriter = new FileWriter(path);
			fileWriter.append(CSV_FILE_HEADER);
			
			// Iterate through all the entries. Add them to the CSV.
			for(Entry entry : entries){
				fileWriter.append(entry.toString());
			}
			
			// Finalize writing to CSV and close it.
			fileWriter.flush();
			fileWriter.close();
		} catch(Exception ex){
			// If anything fails, log it to figure out what's going on
			logger.catching(ex);
		}		
	}
}
