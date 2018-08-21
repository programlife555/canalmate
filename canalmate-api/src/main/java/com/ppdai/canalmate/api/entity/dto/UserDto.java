package com.ppdai.canalmate.api.entity.dto;

import java.util.Date;

/**
 * 用户Dto
 * 不对应数据库表
 * 只是从页面接收数据
 */
public class UserDto extends BaseDto {

    /**
     *
     */
    private static final long serialVersionUID = -3850611166236971181L;
    private Integer pkUserId;
    private String userCode;
    private String userName;
    private String userPassword;
    private String userMobile;
    private String userEmail;
    private String superior;
    private String createUser;
    private String updateUser;
    private String insertTime;
    private String updateTime;
    private Byte isActive;
    private String roleCode;//从页面接收角色代码

    public UserDto(Integer pkUserId, String userCode, String userName, String userMobile, String userEmail, String superior, String createUser, String updateUser, String insertTime, String updateTime, Byte isActive) {
        this.pkUserId = pkUserId;
        this.userCode = userCode;
        this.userName = userName;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.superior = superior;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.insertTime = insertTime;
        this.updateTime = updateTime;
        this.isActive = isActive;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    public Integer getPkUserId() {
        return pkUserId;
    }

    public void setPkUserId(Integer pkUserId) {
        this.pkUserId = pkUserId;
    }

    public UserDto() {
    }

    public UserDto(String userCode, String userName) {
        this.userCode = userCode;
        this.userName = userName;
    }

}
