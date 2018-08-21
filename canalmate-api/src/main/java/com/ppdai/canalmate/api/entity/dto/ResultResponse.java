package com.ppdai.canalmate.api.entity.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/** 
* 
* @author yanxd 
*/
public class ResultResponse {

	private Boolean succeed;
	
	private String reCode;
	private String rsMsg;

	public Boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(Boolean succeed) {
		this.succeed = succeed;
	}

	public String getReCode() {
		return reCode;
	}

	public void setReCode(String reCode) {
		this.reCode = reCode;
	}

	public String getRsMsg() {
		return rsMsg;
	}

	public void setRsMsg(String rsMsg) {
		this.rsMsg = rsMsg;
	}

	public ResultResponse() {
	}

	public ResultResponse(boolean succeed, String reCode, String rsMsg) {
		super();
		this.succeed = succeed;
		this.reCode = reCode;
		this.rsMsg = rsMsg;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
