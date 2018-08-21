package com.ppdai.canalmate.common.utils;
//package com.ppdai.console.common.utils;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import ch.ethz.ssh2.Connection;
//import ch.ethz.ssh2.Session;
//import ch.ethz.ssh2.StreamGobbler;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class ExecShell {
//
//    private static Logger logger = LoggerFactory.getLogger(ExecShell.class);
//    private static String  DEFAULTCHART = "UTF-8";
//
//    public static boolean execute(String cmd, String ip, String userName, String userPwd, int port) {
//        String result = "";
//        boolean flg = false;
//        Session session = null;
//        Connection conn = null;
//        try {
//        	logger.info("=====将要执行命令的环境，ip:"+ip+",  osuser:"+userName+",  cmd:"+cmd);
//            conn = new Connection(ip, port);
//            conn.connect(); // 连接
//            flg = conn.authenticateWithPassword(userName, userPwd); // 认证
//            if (flg) {
//                session = conn.openSession(); // 打开一个会话
//                session.execCommand(cmd); // 执行命令
//                result = processStdout(session.getStdout(), DEFAULTCHART);
//                // 如果为得到标准输出为空，说明脚本执行出错了
//                if (StringUtils.isBlank(result)) {
//                    result = processStdout(session.getStderr(), DEFAULTCHART);
//                }
//                return true;
//            }else{
//                return false;
//            }
//        } catch (IOException e) {
//        	e.printStackTrace();
//            return false;
//        } finally {
//            if (conn != null) {
//                conn.close();
//            }
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//
//    private static String processStdout(InputStream in, String charset) {
//        InputStream stdout = new StreamGobbler(in);
//        StringBuffer buffer = new StringBuffer();
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new InputStreamReader(stdout, charset));
//            String line = null;
//            while((line = br.readLine()) != null) {
//                buffer.append(line + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return buffer.toString();
//    }
//
//}
