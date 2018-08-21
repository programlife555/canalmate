package com.ppdai.canalmate.api.model.canal.server;

import java.util.Date;

import com.ppdai.canalmate.common.utils.DateUtil;

/*
 * 为了给前端展示，有些字段(date)需要做更友好的字符串化，便于前端展示
 * */
public class CanalServerConfigShow extends CanalServerConfig{
  
    private String inserttimeStr;

    private String updatetimeStr;

    private String serverStatusCode;//给前端展示的,canal server的当前状态码 ，1：在运行。0：没运行
    
    private String standbyStatusCode;//给前端展示的,canal standby server的当前状态码 ，1：在运行。0：没运行

    private String canalServerHostPort;//给前端展示的,把server的IP和端口放一起展示
    
    private String standbyServerHostPort;//给前端展示的,把server的IP和端口放一起展示


	public String getCanalServerHostPort() {
		return canalServerHostPort;
	}

	public void setCanalServerHostPort(String canalServerHostPort) {
		this.canalServerHostPort = canalServerHostPort;
	}

	public String getStandbyServerHostPort() {
		return standbyServerHostPort;
	}

	public void setStandbyServerHostPort(String standbyServerHostPort) {
		this.standbyServerHostPort = standbyServerHostPort;
	}

	public String getInserttimeStr() {
		return inserttimeStr;
	}

	public void setInserttimeStr(String inserttimeStr) {
		this.inserttimeStr = inserttimeStr;
	}

	public String getUpdatetimeStr() {
		return updatetimeStr;
	}

	public void setUpdatetimeStr(String updatetimeStr) {
		this.updatetimeStr = updatetimeStr;
	}

	public String getServerStatusCode() {
		return serverStatusCode;
	}

	public void setServerStatusCode(String serverStatusCode) {
		this.serverStatusCode = serverStatusCode;
	}

	public String getStandbyStatusCode() {
		return standbyStatusCode;
	}

	public void setStandbyStatusCode(String standbyStatusCode) {
		this.standbyStatusCode = standbyStatusCode;
	}

    
    
}