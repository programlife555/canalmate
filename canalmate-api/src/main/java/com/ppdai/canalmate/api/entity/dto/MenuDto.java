package com.ppdai.canalmate.api.entity.dto;

import java.util.Date;
import java.util.List;

/**
 * @author yanxd
 */
public class MenuDto extends BaseDto {

    /**
     *
     */
    private static final long serialVersionUID = 4744692506975402047L;
    private Integer pkMenuId;
    private String menuCode;
    private String menuName;
    private String superiorMenu;
    private String menuUrl;
    private String menuIcon;
	private Integer menuSerial;
    private String createUser;
    private String updateUser;
    private String insertTime;
    private String updateTime;
    private Byte isActive;
    private List<MenuDto> children;

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

    public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

    
    public String getSuperiorMenu() {
        return superiorMenu;
    }

    public void setSuperiorMenu(String superiorMenu) {
        this.superiorMenu = superiorMenu;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getMenuSerial() {
        return menuSerial;
    }

    public void setMenuSerial(Integer menuserial) {
        this.menuSerial = menuserial;
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

    public Integer getPkMenuId() {
        return pkMenuId;
    }

    public void setPkMenuId(Integer pkMenuId) {
        this.pkMenuId = pkMenuId;
    }

    public MenuDto() {
    }

    public MenuDto(String menuCode, String menuName, String superiorMenu, String menuUrl,String menuIcon, Integer menuSerial) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.superiorMenu = superiorMenu;
        this.menuUrl = menuUrl;
        this.menuIcon = menuIcon;
        this.menuSerial = menuSerial;
    }

    public MenuDto(Integer pkMenuId, String menuCode, String menuName, String superiorMenu, String menuUrl, Integer menuSerial, String createUser, String updateUser, String insertTime, String updateTime, Byte isActive) {
        this.pkMenuId = pkMenuId;
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.superiorMenu = superiorMenu;
        this.menuUrl = menuUrl;
        this.menuSerial = menuSerial;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.insertTime = insertTime;
        this.updateTime = updateTime;
        this.isActive = isActive;
    }

    public List<MenuDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDto> children) {
        this.children = children;
    }
}
