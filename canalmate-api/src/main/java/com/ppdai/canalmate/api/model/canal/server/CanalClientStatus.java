package com.ppdai.canalmate.api.model.canal.server;

/*不对应任何表，只是包装对象，给前端展示
 * 
 * */
public class CanalClientStatus{

	private String canalServerName;
    
    private String destinationName;

    private String clientAddressPort;
    
    private String mysqlAddress;

	private String color;
	
	private String canalClientStatusCode;
    
    private String comment;
    
    private String clientName;
    
    public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getCanalClientStatusCode() {
		return canalClientStatusCode;
	}

	public void setCanalClientStatusCode(String canalClientStatusCode) {
		this.canalClientStatusCode = canalClientStatusCode;
	}

    
    public String getMysqlAddress() {
    	return mysqlAddress;
    }
    
    public void setMysqlAddress(String mysqlAddress) {
    	this.mysqlAddress = mysqlAddress;
    }

	public String getCanalServerName() {
		return canalServerName;
	}

	public void setCanalServerName(String canalServerName) {
		this.canalServerName = canalServerName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getClientAddressPort() {
		return clientAddressPort;
	}

	public void setClientAddressPort(String clientAddressPort) {
		this.clientAddressPort = clientAddressPort;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
    
    

}

