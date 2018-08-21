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
				      
				      keyValues.put("osuser", osuser);
				      keyValues.put("passwd", passwd);
				      keyValues.put("port", port);
	
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