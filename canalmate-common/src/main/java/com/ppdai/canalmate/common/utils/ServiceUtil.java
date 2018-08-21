package com.ppdai.canalmate.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @auther weitong 2018-05-09
 */


public class ServiceUtil {
	
	private static String osuser=PropertiesUtils.getValue("osuser");
	private static String passwd=PropertiesUtils.getValue("passwd");
	private static Integer port=Integer.parseInt(PropertiesUtils.getValue("port"));

    private ServiceUtil () {}

    /**
     * 获取返回结果
     * @return code:1 msg: success
     */
    public static Map<String, Object> returnSuccess() {

        return returnMessage("1", "success");
    }

    /** A small routine used all over to improve code efficiency, make a result map with the message and the success response code */
    public static Map<String, Object> returnSuccess(String successMessage) {
        return returnMessage("1", successMessage);
    }

    public static Map<String, Object> returnError() {
        Map<String,Object> map = returnMessage("-1", "error");
        map.put("data",null);
        return map;
    }

    public static Map<String, Object> returnError(String errorMessage) {
        return returnMessage("-1", errorMessage);
    }

    public static Map<String, Object> returnMessage(String code, String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (code != null) { result.put("code", code);}
        if (message != null) {result.put("msg", message);}
        return result;
    }

    //创建远程机器的文件夹
    public static boolean prepareRemoteDir(String rmtHost,String rmtDir){
    	boolean result=false;
        String mkDir = "mkdir -p " + rmtDir;
        RmtShellExecutor exe = new RmtShellExecutor(rmtHost, osuser, passwd,port);
        Map<String,Object> resMap = new HashMap<String, Object>();
        try {
            resMap = exe.exec(mkDir);
            Integer ret = (Integer)resMap.get("ret");
            if( ret==0){
                result=true;
            }else {
            	result=false;
            }
        } catch (Exception e) {
        	result=false;
            e.printStackTrace();
        }
        
        return result;
    }

    public static boolean scpFile( String fileName, String configuration, String host,String home){
    	boolean result=false;

    	//先在本地生成临时文件
        FileUtil.writeFile(fileName, configuration, false);
        //把临时文件scp到目标机器的指定目录，这个要求canalMate的节点与所有的host免密登录
        String cmd = "scp -P "+ port+" " + fileName + " "+osuser+"@" + host + ":" + home;
        
//        RmtShellExecutor exe = new RmtShellExecutor("localhost", osuser,passwd,port);
//        Map<String,Object> resMap = exe.exec(cmd);
        
        Map<String,Object> resMap = LocalShellExecutor.executeShell(cmd);

        Integer ret = (Integer)resMap.get("ret");
        if( ret==0){
            result=true;
        }else {
        	result=false;
        }
        return result;
    }


}
