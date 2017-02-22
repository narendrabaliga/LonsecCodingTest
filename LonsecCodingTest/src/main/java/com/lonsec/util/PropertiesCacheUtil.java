package com.lonsec.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import com.lonsec.constants.FixedConstants;

public class PropertiesCacheUtil {
	
	private final Properties configProp = new Properties();
    
	   private PropertiesCacheUtil()
	   {
	      //Private constructor to restrict new instances
	      InputStream in = PropertiesCacheUtil.class.getClassLoader()
	    		  	.getResourceAsStream(FixedConstants.OUTPERFORMANC_PROPERTY_FILE_NAME);
	      try {
	    	  if(in != null)
	          configProp.load(in);
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	   }
	 
	    private static class PropertiesCacheHolder
	   {
	      private static final PropertiesCacheUtil INSTANCE = new PropertiesCacheUtil();
	   }
	 
	   public static PropertiesCacheUtil getInstance()
	   {
	      return PropertiesCacheHolder.INSTANCE;
	   }
	    
	   public String getProperty(String key){
	      return configProp.getProperty(key);
	   }
	    
	   public Set<String> getAllPropertyNames(){
	      return configProp.stringPropertyNames();
	   }
	    
	   public boolean containsKey(String key){
	      return configProp.containsKey(key);
	   }

}
