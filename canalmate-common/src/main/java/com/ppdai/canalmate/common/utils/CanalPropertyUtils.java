package com.ppdai.canalmate.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.position.LogPosition;
import com.ppdai.canalmate.common.cons.CanalConstants;
import com.ppdai.canalmate.common.model.ZKDestinationBean;
import com.ppdai.canalmate.common.model.ZKDestinationClientRunningNode;
import com.ppdai.canalmate.common.model.ZKDestinationClusterNode;
import com.ppdai.canalmate.common.model.ZKDestinationRunningNode;

/*
 * 读取canal的properties信息
 * */
public class CanalPropertyUtils {
	
    
    public static String getPropertyValueByKey(String configProperty,String key) {
//    	P.p(configProperty);
//    	P.p("key:"+key);
    	InputStream inStream = new ByteArrayInputStream(configProperty.getBytes(StandardCharsets.UTF_8));
		Properties prop = new Properties();
		try {
			prop.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value=prop.getProperty(key);
    	
    	return value;
    	
    }
    
}
