package com.ppdai.canalmate.api.model.canal.server;

import java.util.Date;

/*
* canal 系统的报警消息的封装bean
* */
public class CanalWarn {
    private Long id;

    private String warnType;

    private String warnMessage;

    private Date warnTime;

    private Date inserttime;

    private Date updatetime;

    private Boolean isactive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarnType() {
        return warnType;
    }

    public void setWarnType(String warnType) {
        this.warnType = warnType == null ? null : warnType.trim();
    }

    public String getWarnMessage() {
        return warnMessage;
    }

    public void setWarnMessage(String warnMessage) {
        this.warnMessage = warnMessage == null ? null : warnMessage.trim();
    }

    public Date getWarnTime() {
        return warnTime;
    }

    public void setWarnTime(Date warnTime) {
        this.warnTime = warnTime;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }
}