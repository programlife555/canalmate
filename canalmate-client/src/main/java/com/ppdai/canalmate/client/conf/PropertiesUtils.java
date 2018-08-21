package com.ppdai.canalmate.client.conf;

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
				 
		    	  in=PropertiesUtils.class.getResourceAsStream("/application.properties");
			      properties.load(in);
			      
			      String url = properties.getProperty("spring.datasource.url");
			      String  username = properties.getProperty("spring.datasource.username");
			      String  password = properties.getProperty("spring.datasource.password");
			      String  driverName = properties.getProperty("spring.datasource.driver-class-name");
	
			      
			      keyValues.put("url", url);
			      keyValues.put("username", username);
			      keyValues.put("password", password);
			      keyValues.put("driverName", driverName);
			      
			      
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
		 
  public static void main(String[] args)
  {
	  System.out.println("value======"+PropertiesUtils.getValue("driverName"));
  }

  public  static String getValue(String a)
  {
		  return keyValues.get(a);
  }
}