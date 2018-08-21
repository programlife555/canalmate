package com.ppdai.canalmate.api.entity.dto;

import java.util.Date;

/**
 * 用户权限Dto
 * @author yanxd
 */
public class UserAuthDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2782492947307156898L;

	private Integer pkUserAuthCode;
	private String userCode;
	private String userName;
	private String authCode;
	private String authName;
	private String createUser;
	private String updateUser;
	private String insertTime;
	private String updateTime;
	private Byte isActive;

	public Integer getPkUserAuthCode() {
		return pkUserAuthCode;
	}

	public void setPkUserAuthCode(Integer pkUserAuthCode) {
		this.pkUserAuthCode = pkUserAuthCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public Byte getIsActive() {
		return isActive;
	}

	public void setIsActive(Byte isActive) {
		this.isActive = isActive;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public UserAuthDto(Integer pkUserAuthCode, String userCode, String userName, String authCode, String authName, String createUser, String updateUser, String insertTime, String updateTime, Byte isActive) {
		this.pkUserAuthCode = pkUserAuthCode;
		this.userCode = userCode;
		this.userName = userName;
		this.authCode = authCode;
		this.authName = authName;
		this.createUser = createUser;
		this.updateUser = updateUser;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
		this.isActive = isActive;
	}

	public UserAuthDto() {
		super();
	}

}
