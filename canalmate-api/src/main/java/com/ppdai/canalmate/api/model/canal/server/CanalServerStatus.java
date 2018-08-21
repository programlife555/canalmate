package com.ppdai.canalmate.api.model.canal.server;

/*不对应任何表，只是包装对象，给前端展示
 * 
 * */
public class CanalServerStatus{
    private String canalServerType;

    private String canalHome;

    private String canalServerName;

    private String addressPort;

    private String color;
    
    private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCanalServerType() {
		return canalServerType;
	}

	public void setCanalServerType(String canalServerType) {
		this.canalServerType = canalServerType;
	}

	public String getCanalHome() {
		return canalHome;
	}

	public void setCanalHome(String canalHome) {
		this.canalHome = canalHome;
	}

	public String getCanalServerName() {
		return canalServerName;
	}

	public void setCanalServerName(String canalServerName) {
		this.canalServerName = canalServerName;
	}

	public String getAddressPort() {
		return addressPort;
	}

	public void setAddressPort(String addressPort) {
		this.addressPort = addressPort;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
