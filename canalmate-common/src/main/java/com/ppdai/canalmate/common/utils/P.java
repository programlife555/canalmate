package com.ppdai.canalmate.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class P {
	private static File logFile = new File("printUtils.log");
	
	static{ 
		  if(!logFile.exists()) {
			  try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		  }
	}
	
	public static void  print(Object  log) { //length表示生成字符串的长度
	    System.out.println(DateUtil.getCurrentDateStr()+"  "+log.toString());
	 }
	
	public static void  p(Object  log) { //length表示生成字符串的长度
	    System.out.println(log.toString());
	 }
	
	public static void  pr(Object  log) { //length表示生成字符串的长度
	    System.out.println("=========="+log.toString());
	 }
	
	public static void printEx(Exception e) {  
        try {
            StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            P.print("==\r\n" + sw.toString() + "\r\n");
            //return "\r\n" + sw.toString() + "\r\n";  
        } catch (Exception e2) {  
        	P.print("bad printEx");
        }  
    }
	
	public static void  appendToFile(Object  log) {
	    FileWriter fw=null;
	    BufferedWriter bw=null;
		try {
			fw = new FileWriter(logFile,true);
			bw = new BufferedWriter(fw);
			bw.write(DateUtil.getCurrentDateStr()+"  "+log.toString()+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bw!=null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fw!=null){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	 }
	
	public static void  appendToFile(String className,Object  log) {
	    FileWriter fw=null;
	    BufferedWriter bw=null;
		try {
			fw = new FileWriter(logFile,true);
			bw = new BufferedWriter(fw);
			bw.write(DateUtil.getCurrentDateStr()+" "+className+" "+log.toString()+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bw!=null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fw!=null){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	 }
	
	public static String getStackMsg(Exception e) {  
		  
        StringBuffer sb = new StringBuffer();  
        StackTraceElement[] stackArray = e.getStackTrace();  
        for (int i = 0; i < stackArray.length; i++) {  
            StackTraceElement element = stackArray[i];  
            sb.append(element.toString() + "\n");  
        }  
        return sb.toString();  
    }  
  
	public static String getStackMsg(Throwable e) {  
  
        StringBuffer sb = new StringBuffer();  
        StackTraceElement[] stackArray = e.getStackTrace();  
        for (int i = 0; i < stackArray.length; i++) {  
            StackTraceElement element = stackArray[i];  
            sb.append(element.toString() + "\n");  
        }  
        return sb.toString();  
    }  


}
