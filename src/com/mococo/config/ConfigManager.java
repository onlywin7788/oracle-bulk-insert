package com.mococo.config;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Node;

import com.mococo.util.XMLParser;


public class ConfigManager {

	String configPath = "";
	XMLParser xmlParser = null;
	
	public String dbConn;
	public String dbId = "";
	public String dbPass = "";
	public String SQL = "";
	public int totalCount = 0;
	public int commitCount = 0;
	
	
	public ArrayList<HashMap<String, String>> paramList = new ArrayList<HashMap<String, String>>();

	public ConfigManager(String configPath)
	{
		try
		{
			xmlParser = new XMLParser();
			this.configPath = configPath;

			loadConfigFile();
			loadConfig();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void loadConfigFile() throws Exception
	{
		try
		{
			xmlParser.loadFile(configPath);
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	public void loadConfig() throws Exception
	{
		try
		{
			totalCount = Integer.parseInt(xmlParser.getNodeAttr("/config/common/insert", "total_count"));
			commitCount = Integer.parseInt(xmlParser.getNodeAttr("/config/common/insert", "commit_count"));
			
			dbConn = xmlParser.getNodeAttr("/config/database", "conn");
			dbId = xmlParser.getNodeAttr("/config/database", "id");
			dbPass = xmlParser.getNodeAttr("/config/database", "pass");
			SQL = xmlParser.getNodeText("/config/data/SQL");
			
			HashMap<String, String> paramMap = new HashMap<>();
			ArrayList<Node> nodeList = xmlParser.getNodeList("/config/data/params/param");

			for(int i=0; i<nodeList.size(); i++)
			{
				Node node = nodeList.get(i);
				String length = xmlParser.getNodeAttrFromNode(node, "length");
				String value = xmlParser.getNodeAttrFromNode(node, "value");

				paramMap.put("length", length);
				paramMap.put("value", value);
				
				paramList.add(paramMap);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}

}
