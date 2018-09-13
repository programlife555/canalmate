package com.ppdai.canalmate.common.model;

import java.util.Date;

public class ZKDestinationClusterNode {
    private String addressPort;//例如： IP:11111，从zk获取
    private Boolean active;//需要手动计算，set进去
    
	public String getAddressPort() {
		return addressPort;
	}
	public void setAddressPort(String addressPort) {
		this.addressPort = addressPort;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
    
    
    

}