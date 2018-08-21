package com.ppdai.canalmate.common.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//http://kongcodecenter.iteye.com/blog/1231177
//参考http://siye1982.iteye.com/blog/592405
//参考http://blog.csdn.net/christophe2008/article/details/6046456
public class LocalShellExecutor {
	
    private static Logger logger = LoggerFactory.getLogger(LocalShellExecutor.class);

	// 基本路径
	private static final String basePath = "/home/";

	// 记录Shell执行状况的日志文件的位置(绝对路径)
	private static final String executeShellLogFile = basePath + "xxx.log";

	// 发送文件到Kondor系统的Shell的文件名(绝对路径)
	private static final String sendKondorShellName = basePath + "xxx.sh";

	public static Map<String ,Object> executeShell(String shellCommand) {
		logger.info("===将要在本地执行的shellCommand:" + shellCommand);
		int success = -1;// 如果脚本执行的返回值不是0,则表示脚本执行失败，否则（值为0）脚本执行成功。0:成功，非0：失败
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = null;
		BufferedReader stdError = null;
		// 格式化日期时间，记录日志时使用
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS ");
        Map<String,Object> resultMap = new HashMap<String, Object>();

		try {
			stringBuffer.append(dateFormat.format(new Date())).append("准备执行Shell命令 ").append(shellCommand)
					.append(" \r\n");

			Process pid = null;
			String[] cmd = { "/bin/sh", "-c", shellCommand };
			// 执行Shell命令
			pid = Runtime.getRuntime().exec(cmd);
			if (pid != null) {
				stringBuffer.append("进程号：").append(pid.toString()).append("\r\n");

				// bufferedReader用于读取Shell的输出内容
				bufferedReader = new BufferedReader(new InputStreamReader(pid.getInputStream()));
				// 读到标准出错的信息
				stdError = new BufferedReader(new InputStreamReader(pid.getErrorStream()));
				// 这个是或得脚本执行的返回值
				int status = pid.waitFor();

				// 如果脚本执行的返回值不是0,则表示脚本执行失败，否则（值为0）脚本执行成功。
				if (status != 0) {
					stringBuffer.append("shell脚本执行失败！");
				} else {
					stringBuffer.append("shell脚本执行成功！");
					success=0;
				}
			} else {
				stringBuffer.append("没有pid\r\n");
			}
			stringBuffer.append(dateFormat.format(new Date())).append("Shell命令执行完毕\r\n执行结果为：\r\n");

			// 将标准输入流上面的内容写到stringBuffer里面
			String line = null;
			// 读取Shell的输出内容，并添加到stringBuffer中
			while (bufferedReader != null && (line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line).append("\r\n");
		        resultMap.put("outStr",line);

			}

			// 将标准输入流上面的内容写到stringBuffer里面
			String line1 = null;
			while (stdError != null && (line1 = stdError.readLine()) != null) {
				stringBuffer.append(line1).append("\r\n");
		        resultMap.put("outErr",line);

			}
		} catch (Exception ioe) {
			stringBuffer.append("执行Shell命令时发生异常：\r\n").append(ioe.getMessage()).append("\r\n");
			logger.error(stringBuffer.toString());
		} finally {
			if (bufferedReader != null) {
				OutputStreamWriter outputStreamWriter = null;
				try {
					bufferedReader.close();
					// 将Shell的执行情况输出到日志文件中
//					OutputStream outputStream = new FileOutputStream(executeShellLogFile);
//					outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
//					outputStreamWriter.write(stringBuffer.toString());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					//outputStreamWriter.close();
				}
			}
		}
		
        resultMap.put("ret",success);

		return resultMap;
	}

	public static void main(String[] args) {
		LocalShellExecutor.executeShell(sendKondorShellName);
		
	}
}