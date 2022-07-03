package com.mococo.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonLogger {

	public String getTimeStamp()
	{
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		String timestamp = dataFormat.format(new Date());
		
		return timestamp;
	}
	
	public void info(String contents)
	{
		System.out.format("[%s][INFO] %s\n", getTimeStamp(), contents);
	}
	
	public void debug(String contents)
	{
		System.out.format("[%s][DEBG] %s\n", getTimeStamp(), contents);
	}
	
	public void error(String contents)
	{
		System.out.format("[%s][ERRO] %s\n", getTimeStamp(), contents);
	}
	
	
	
}
