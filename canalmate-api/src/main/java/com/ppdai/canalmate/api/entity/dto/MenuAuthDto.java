package com.ppdai.canalmate.api.entity.dto;

import java.util.Date;

/**
 * 菜单权限Dto
 *
 * @author yanxd
 */
public class MenuAuthDto extends BaseDto {

    /**
     *
     */
    private static final long serialVersionUID = -2405141546989546547L;

    private Integer pkMenuAuthCode;
    private String menuCode;
    private String menuName;
    private String authCode;
    private String authName;
    private String createUser;
    private String updateUser;
    private String insertTime;
    private String updateTime;
    private Byte isActive;

    public Integer getPkMenuAuthCode() {
        return pkMenuAuthCode;
    }

    public void setPkMenuAuthCode(Integer pkMenuAuthCode) {
        this.pkMenuAuthCode = pkMenuAuthCode;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
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

    public MenuAuthDto(Integer pkMenuAuthCode, String menuCode, String menuName, String authCode, String authName, String createUser, String updateUser, String insertTime, String updateTime, Byte isActive) {
        this.pkMenuAuthCode = pkMenuAuthCode;
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.authCode = authCode;
        this.authName = authName;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.insertTime = insertTime;
        this.updateTime = updateTime;
        this.isActive = isActive;
    }

    public MenuAuthDto() {
        super();
    }


}
