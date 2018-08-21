package com.ppdai.canalmate.api.entity.dto;

public class LoginResponse extends ResultResponse{

	private Boolean succeed;
	
	private String reCode;
	private String rsMsg;
	private String isAdmin;//是否是admin角色的标志，1：是，0：不是

	public LoginResponse(boolean succeed, String reCode, String rsMsg) {
		super();
		this.succeed = succeed;
		this.reCode = reCode;
		this.rsMsg = rsMsg;
	}
	
	public LoginResponse() {
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
}
