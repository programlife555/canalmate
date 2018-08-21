package com.ppdai.canalmate.api.entity.dto;

/**
 * Created by gongyun on 2018/3/14.
 */
public class RateAlarmConfigDto {
    private Integer id;
    private String type;
    private String key;
    private String bizid;
    private String dingid;
    private Boolean isPlusCompensation;
    private String rates;
    private String value;
    private String createUser;
    private String updateUser;
    private String insertTime;
    private String updateTime;
    private Byte isActive;
    private String remark;

    public RateAlarmConfigDto(Integer id, String type, String key, String bizid, String dingid, Boolean isPlusCompensation, String rates, String value, String createUser, String updateUser, String insertTime, String updateTime, Byte isActive, String remark) {
        this.id = id;
        this.type = type;
        this.key = key;
        this.bizid = bizid;
        this.dingid = dingid;
        this.isPlusCompensation = isPlusCompensation;
        this.rates = rates;
        this.value = value;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.insertTime = insertTime;
        this.updateTime = updateTime;
        this.isActive = isActive;
        this.remark = remark;
    }

    public RateAlarmConfigDto() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBizid() {
        return bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid;
    }

    public String getDingid() {
        return dingid;
    }

    public void setDingid(String dingid) {
        this.dingid = dingid;
    }

    public Boolean getPlusCompensation() {
        return isPlusCompensation;
    }

    public void setPlusCompensation(Boolean plusCompensation) {
        isPlusCompensation = plusCompensation;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
