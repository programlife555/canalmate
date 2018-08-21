package com.ppdai.canalmate.api.entity.dto;


/**
 * 不对应mybatis，只是程序中当bean用。
 */
public class UserRoleDto extends BaseDto {

    private static final long serialVersionUID = 2303605193469084065L;
    private Integer pkUserRoleId;
    private String userCode;
    private String userName;
    private String roleCode;
    private String roleName;
    private String createUser;
    private String updateUser;
    private String insertTime;
    private String updateTime;
    private Byte isActive;

    public Integer getPkUserRoleId() {
        return pkUserRoleId;
    }

    public void setPkUserRoleId(Integer pkUserRoleId) {
        this.pkUserRoleId = pkUserRoleId;
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

    public UserRoleDto() {
        super();
    }

    public UserRoleDto(String userCode, String roleCode) {
        this.userCode = userCode;
        this.roleCode = roleCode;
    }

    public UserRoleDto(String userCode, String userName, String roleCode, String roleName) {
        this.userCode = userCode;
        this.userName = userName;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public UserRoleDto(Integer pkUserRoleId, String userCode, String userName, String roleCode, String roleName, String createUser, String updateUser, String insertTime, String updateTime, Byte isActive) {
        this.pkUserRoleId = pkUserRoleId;
        this.userCode = userCode;
        this.userName = userName;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.insertTime = insertTime;
        this.updateTime = updateTime;
        this.isActive = isActive;
    }
}
