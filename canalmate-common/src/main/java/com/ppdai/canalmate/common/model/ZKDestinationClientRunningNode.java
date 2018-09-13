package com.ppdai.canalmate.common.model;

import java.util.Date;

/*
 * 得到消费该destination的客户端的运行节点信息
 * 封装从/otter/canal/destinations/ppdai_user/1001/running得到的json
 * 例子：{"active":true,"address":"IP:51602","clientId":1001}
 * */
public class ZKDestinationClientRunningNode {
    private String clientId;
    private String address;
    private Boolean active;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
    
}