package com.mococo.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.mococo.config.ConfigManager;
import com.mococo.logger.CommonLogger;
import com.mococo.util.CommonUtil;

public class DBService
{

	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	ConfigManager configManager = null;
	
	CommonUtil commonUtil = new CommonUtil();
	CommonLogger logger = new CommonLogger();

	public DBService(ConfigManager configManager)
	{
		this.configManager = configManager;
	}

	public void connection() throws Exception
	{
		try
		{
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(
					configManager.dbConn, 
					configManager.dbId,
					configManager.dbPass
					);	
			conn.setAutoCommit(false);

			logger.info("Connection Successed :" + configManager.dbConn);

		}
		catch(Exception e)
		{
			throw e;
		}
	}

	public PreparedStatement fillDataValue(PreparedStatement pstmt) throws Exception
	{
		try
		{
			ArrayList<HashMap<String, String>> paramList = configManager.paramList;
			for(int paramIdx= 0; paramIdx< paramList.size(); paramIdx++)
			{
				HashMap<String, String> paramMap = paramList.get(paramIdx);

				String value = "";
				int length = Integer.parseInt(paramMap.get("length"));
				String defaultValue = paramMap.get("value");

				/*
				for(int valueIdx = 0; valueIdx < length; valueIdx++)
				{
					value += defaultValue;
				}
				*/
				
				value = commonUtil.getRandomString(length);

				pstmt.setString(paramIdx+1, value) ;
			}		
		}
		catch(Exception e)
		{
			throw e;
		}
		
		return pstmt;
	}
	
	
	public void executeBulkInsert() throws Exception
	{
		try
		{
			pstmt = conn.prepareStatement(configManager.SQL) ;
			
			int insertIdx = 0;
			
			for(insertIdx=0; insertIdx < configManager.totalCount; insertIdx++){
				
				fillDataValue(pstmt);
				pstmt.addBatch();
				// OutOfMemory를 고려하여 만건 단위로 커밋
				if( (insertIdx % configManager.commitCount) == 0){

					// Batch 실행
					pstmt.executeBatch() ;
					conn.commit() ;	

					logger.info("CURRENT COMMIT ROWS : " + insertIdx);
				}
			}

			pstmt.executeBatch() ;
			conn.commit() ;
			
			logger.info("LAST COMMIT ROWS : " + insertIdx);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
					pstmt = null;
				}
				catch(Exception e)
				{
					throw e;
				}
			}
		}
	}
}
