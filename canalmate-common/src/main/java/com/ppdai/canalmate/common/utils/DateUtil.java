package com.ppdai.canalmate.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * Created by wangruixiang on 2017/7/19.
 */
public class DateUtil {
    public static String formatDate(String dateStr) {
        if (dateStr.indexOf(".")!=-1){
            return dateStr.substring(0,dateStr.length()-2);
        }else{
            return  dateStr;
        }
    }
    
    /** 
     * 将当前时间转为UNIX时间戳
     * @return 
     * @throws ParseException 
     */  
    public static long  getUnixTimestampFromCurrentDate() {  
    	long epoch=0;
    	try{
    		 epoch = System.currentTimeMillis();
    		epoch= epoch/1000;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return epoch;
    }
    
    /** 
     * 将当前时间转为UTC时间戳
     * @param UTCTime 
     * @return 
     * @throws ParseException 
     */  
    public static long  getUTCTimeFromCurrentDate() {  
    	long epoch=0;
    	try{
    		 epoch = System.currentTimeMillis();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return epoch;
    } 
    
    /**
     * 获取当前日期和时间 yyyyMMdd HH:mm:ss
     * 
     * @return String
     */
    public static String getCurrentDateStr() {
		 Date date = new Date();
		 String str = null;
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 str = df.format(date);
		 return str;
    }
    
    public static Date parseDate(String source, String pattern) {
		 SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		 return sdf.parse(source, new ParsePosition(0));
    }
    
	public static String DateToStr(Date date, String... pattern) {
		String dateStr = null;
		SimpleDateFormat df =null;
		if (pattern != null && pattern.length > 0) {
			df = new SimpleDateFormat(pattern[0]);
		} else {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		dateStr=df.format(date);
		return dateStr;
	}
	/*
	 * 用在把java.sql.timestamp转为 yyyy-mm-dd等人类方便识别的格式
	 * */
	public static String TimeStampToStr(Timestamp timeStamp, String... pattern) {
		String dateStr = null;
		SimpleDateFormat df =null;
		if (pattern != null && pattern.length > 0) {
			df = new SimpleDateFormat(pattern[0]);
		} else {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		dateStr=df.format(timeStamp);
		return dateStr;
	}
	
    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     * @param timestampString 时间戳 如："1473048265";
     * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     *
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String... pattern) {

		String formats=null;
		if (pattern != null && pattern.length > 0) {
			formats=pattern[0];
		} else {
			formats="yyyy-MM-dd HH:mm:ss";
		}
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }
    
    /**
     * Java将UTC时间戳(毫秒milliseconds)转换成指定格式日期字符串
     * @param timestampString 时间戳 如："1473048265";
     * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     *
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String UTCTime2Date(String timestampString, String... pattern) {

		String formats=null;
		if (pattern != null && pattern.length > 0) {
			formats=pattern[0];
		} else {
			formats="yyyy-MM-dd HH:mm:ss";
		}
        Long timestamp = Long.parseLong(timestampString);
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }
}
