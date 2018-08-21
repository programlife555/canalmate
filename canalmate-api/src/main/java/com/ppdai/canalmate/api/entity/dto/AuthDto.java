package com.ppdai.canalmate.api.entity.dto;

import java.util.Date;

/**
 * 权限Dto
 *
 * @author yanxd
 */
public class AuthDto extends BaseDto {

    /**
     *
     */
    private static final long serialVersionUID = -2162113965301462454L;
    private Integer pkAuthId;
    private String authCode;
    private String authName;
    private String createUser;
    private String updateUser;
    private String insertTime;
    private String updateTime;
    private Byte isActive;

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

    public Integer getPkAuthId() {
        return pkAuthId;
    }

    public void setPkAuthId(Integer pkAuthId) {
        this.pkAuthId = pkAuthId;
    }

    public AuthDto(String authCode, String authName) {
        this.authCode = authCode;
        this.authName = authName;
    }

    public AuthDto() {
        super();
    }

    public AuthDto(Integer pkAuthId, String authCode, String authName, String createUser, String updateUser, String insertTime, String updateTime, Byte isActive) {
        this.pkAuthId = pkAuthId;
        this.authCode = authCode;
        this.authName = authName;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.insertTime = insertTime;
        this.updateTime = updateTime;
        this.isActive = isActive;
    }
}
