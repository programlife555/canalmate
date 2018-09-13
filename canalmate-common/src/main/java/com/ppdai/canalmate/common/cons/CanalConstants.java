package com.ppdai.canalmate.common.cons;

public class CanalConstants {

	  public final static String UTF_8 = "UTF-8";
	  public final static String PWD_postfix = "gold";//给密码加密的后缀
	  public final static Short clientId = 1001; //canal client默认的clientId
	  public final static Integer zk_session_timeout=10000;
	  
	  public final static String GREEN = "green";//绿色，正常运行
	  public final static String RED = "red";//红色，节点挂了
	  public final static String YELLOW = "yellow";//黄色，db配置错误，与zk里面的信息不符
	  public final static String GRAY = "gray";//灰色，standby状态运行
//	  public final static Long GAP_HOUR_threshold = 1L;//延迟gap认为不正常的阈值,单位是小时
//	  public final static Long GAP_MIN_threshold = 20L;//延迟gap认为不正常的阈值,单位是分钟

	  public final static String RUNNING = "1";//正在运行
	  public final static String NOT_RUNNING = "0";//没有运行


}
