package com.ppdai.canalmate.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @auther weitong 2018-05-09
 */


public class DBUtils {
    private static Logger logger = LoggerFactory.getLogger(DBUtils.class);


    private DBUtils () {}

    /**
     * 获取mysql数据块的当前位点
     * @return code:1 msg: success
     */
    public static Map<String,String> getDBMasterStatus(String ip_port,String dbUsername,String dbPassword) {
   	 
    	Map<String,String> result=new HashMap<String,String>();
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://"+ip_port+"";
        Connection conn=null;
        Statement stm =null;
        ResultSet rs =null;
        try {
            Class.forName(driver);
//            P.p("======================url:"+url+",dbUsername:"+dbUsername+",dbPassword:"+dbPassword);
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            stm = conn.createStatement();
            rs = stm.executeQuery("show master status");
            if (!rs.next()) {
            	throw new Exception( 
            			"Error getting master status; is the MySQL binlog enabled?"); 
            }
            String binlogFile = rs.getString(1); 
            String binlogOffset = rs.getString(2);
            
            result.put("code", ReponseEnum.SUCCEED.getResCode());
            result.put("binlogFile", binlogFile);
            result.put("binlogOffset", binlogOffset);
      
        } catch (Exception e) {
//            e.printStackTrace();
            result.put("code", ReponseEnum.FAIL.getResCode());
        }finally {
        	cleanUpDatabaseResources(conn,stm,rs);
        }
        return result;
    }
    
    // Utility method to close result, statement, and connection objects. 
    private static void cleanUpDatabaseResources(Connection conn, Statement st, ResultSet rs)
    { 
        if (rs != null) 
        { 
            try 
            { 
                rs.close(); 
            } 
            catch (Exception e) 
            { 
            	e.printStackTrace();
            } 
        } 
        if (st != null) 
        { 
            try 
            { 
                st.close(); 
            } 
            catch (Exception e) 
            { 
            	e.printStackTrace();
            } 
        } 
        if (conn != null) 
        { 
        	try 
            { 
                conn.close(); 
            } 
            catch (Exception e) 
            { 
            	e.printStackTrace();
            } 
        } 
    }


}
