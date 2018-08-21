package com.ppdai.canalmate.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;


public class StrUtil {
	
	/*
	 * 判断字段值，若为null,则转为''
	 * */
    public static String IfNullToKong(String str) {
        if (StringUtils.isBlank(str)){
            return "";
        }else{
            return str;
        }
    }
    
    
}
