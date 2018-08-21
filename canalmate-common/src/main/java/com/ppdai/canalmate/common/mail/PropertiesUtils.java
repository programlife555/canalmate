package com.ppdai.canalmate.common.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils 
{
	private static Properties properties = new Properties();

	static {
	  try {
		   InputStream is =PropertiesUtils.class.getResourceAsStream("/conf.properties");
	    	properties.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
  public static void main(String[] args)
  {
	  PropertiesUtils p=new PropertiesUtils();
	  System.out.println("value======"+PropertiesUtils.getValue("smtp.receiver"));
  }

  public static String getValue(String a)
  {
		  return properties.getProperty(a);
  }
}