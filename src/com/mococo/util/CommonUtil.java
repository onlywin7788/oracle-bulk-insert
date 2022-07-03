package com.mococo.util;
import org.apache.commons.lang3.RandomStringUtils;

public class CommonUtil {

	public String getRandomString(int length)
	{
		return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
	}
	
}
