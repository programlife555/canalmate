package com.ppdai.canalmate.api.entity.dto;


/**
 * Created by gongyun on 2017/11/21.
 */
public class RoleDto extends BaseDto {

    private static final long serialVersionUID = 5294002722612929220L;
    private Integer pkRoleId;
    private String roleCode;
    private String roleName;
    private String createUser;
    private String updateUser;
    private String insertTime;
    private String updateTime;
    private Byte isActive;
    

	public Integer getPkRoleId() {
        return pkRoleId;
    }

    public void setPkRoleId(Integer pkRoleId) {
        this.pkRoleId = pkRoleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public RoleDto() {
        super();
    }

    public RoleDto(String roleCode, String roleName) {
        this.roleCode = roleCode;
        this.roleName = roleName;
    }


    public RoleDto(Integer pkRoleId, String roleCode, String roleName, String createUser, String updateUser, String insertTime, String updateTime, Byte isActive) {
        this.pkRoleId = pkRoleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.insertTime = insertTime;
        this.updateTime = updateTime;
        this.isActive = isActive;
    }
}
