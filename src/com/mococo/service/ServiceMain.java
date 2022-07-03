package com.mococo.service;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.mococo.config.ConfigManager;
import com.mococo.database.DBService;
import com.mococo.logger.CommonLogger;

public class ServiceMain {
	
	
	public static void main(String[] args) {
		
		CommonLogger logger = new CommonLogger();
		
		try
		{	
			if (args.length == 0) {
			      System.err.println("\n\nplease input config_file...\n\n");
			      System.exit(1);
			    }

			
			
			ConfigManager configManager = new ConfigManager(args[0]);
			DBService dbService = new DBService(configManager);
				
			logger.info("------------------ PROGRAM START -----------------");
			dbService.connection();
			dbService.executeBulkInsert();
			
			logger.info("------------------ PROGRAM ENDED -----------------");
		}
		catch(Exception e)
		{
			String errorMessage = getPrintStackTrace(e);
			logger.error(errorMessage);
		}
	}
	
	 public static String getPrintStackTrace(Exception e) {
         
	        StringWriter errors = new StringWriter();
	        e.printStackTrace(new PrintWriter(errors));
	         
	        return errors.toString();
	         
	    }
	
}
