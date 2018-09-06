package com.ppdai.canalmate.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class PropertiesUtils 
{
	  private static Properties properties = new Properties();
	  private static Map<String,String> keyValues=new HashMap<String,String>();
	  private static InputStream in = null;
		
		 static {
		    	 try {
			    	  in=PropertiesUtils.class.getResourceAsStream("/conf.properties");
				      properties.load(in);
				      
				      String osuser = properties.getProperty("osuser");
				      String  passwd = properties.getProperty("passwd");
				      String  port = properties.getProperty("port");
				      String PROTOCOL = properties.getProperty("smtp.PROTOCOL");
				      String  HOST = properties.getProperty("smtp.HOST");
				      String  PORT = properties.getProperty("smtp.PORT");
				      String  IS_AUTH = properties.getProperty("smtp.IS_AUTH");
				      String  IS_ENABLED_DEBUG_MOD = properties.getProperty("smtp.IS_ENABLED_DEBUG_MOD");
				      String  from = properties.getProperty("smtp.from");
				      String  gapHour = properties.getProperty("gap.hour");
				      String  gapMin = properties.getProperty("gap.min");
				      
				      keyValues.put("osuser", osuser);
				      keyValues.put("passwd", passwd);
				      keyValues.put("port", port);
				      
				      keyValues.put("PROTOCOL", PROTOCOL);
				      keyValues.put("HOST", HOST);
				      keyValues.put("PORT", PORT);
				      keyValues.put("IS_AUTH", IS_AUTH);
				      keyValues.put("IS_ENABLED_DEBUG_MOD", IS_ENABLED_DEBUG_MOD);
				      keyValues.put("from", from);
				      
				      keyValues.put("gapHour", gapHour);
				      keyValues.put("gapMin", gapMin);
	
				      properties.clear();
		    		 
		    	 }
				 catch (Exception e)
			     {
			       e.printStackTrace();
			     }finally{
			    	 try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			     }
		    }
		 
		 public  static String getValue(String a)
		 {
			 return keyValues.get(a);
		 }
		 
		  public static void main(String[] args)
		  {
			  System.out.println("value======"+PropertiesUtils.getValue("osuser"));
		  }

}