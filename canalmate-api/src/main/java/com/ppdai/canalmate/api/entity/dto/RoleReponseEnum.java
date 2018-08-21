package com.ppdai.canalmate.api.entity.dto;

/**
 * Created by gongyun on 2017/11/21.
 */
public enum RoleReponseEnum {

    SUCCEED("000", "操作成功"), FAIL("001", "操作失败"), EXIST("002", "数据已存在"), ALARM_NOT_EXIST("003", "告警不存在"),
    ALARMCODE_NOT_NUMBER("004", "告警编号非数字"), ALARBUSINESS_NOT_EXIST("005", "角色不存在"),USER_NOT_EXIST("007", "用户不存在"),
    ROLE_NOT_EXIST("008", "角色不存在");

    private String resCode;

    private String resMsg;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    private RoleReponseEnum(String resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

}