package com.ppdai.canalmate.api.model.canal.server;

import java.util.Date;

import com.ppdai.canalmate.common.utils.DateUtil;

public class CanalServerConfig {
    private Long id;

    private String canalServerType;

    private String canalHome;

    private String canalServerName;

    private String canalServerHost;

    private String canalServerPort;

    private String canalServerConfiguration;

    private String standbyServerHost;

    private String standbyServerPort;

    private String standbyServerConfiguration;

    private Date inserttime;

    private Date updatetime;

    private Boolean isactive;

    private String serverName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCanalServerType() {
        return canalServerType;
    }

    public void setCanalServerType(String canalServerType) {
        this.canalServerType = canalServerType == null ? null : canalServerType.trim();
    }

    public String getCanalHome() {
        return canalHome;
    }

    public void setCanalHome(String canalHome) {
        this.canalHome = canalHome == null ? null : canalHome.trim();
    }

    public String getCanalServerName() {
        return canalServerName;
    }

    public void setCanalServerName(String canalServerName) {
        this.canalServerName = canalServerName == null ? null : canalServerName.trim();
    }

    public String getCanalServerHost() {
        return canalServerHost;
    }

    public void setCanalServerHost(String canalServerHost) {
        this.canalServerHost = canalServerHost == null ? null : canalServerHost.trim();
    }

    public String getCanalServerPort() {
        return canalServerPort;
    }

    public void setCanalServerPort(String canalServerPort) {
        this.canalServerPort = canalServerPort == null ? null : canalServerPort.trim();
    }

    public String getCanalServerConfiguration() {
        return canalServerConfiguration;
    }

    public void setCanalServerConfiguration(String canalServerConfiguration) {
        this.canalServerConfiguration = canalServerConfiguration == null ? null : canalServerConfiguration.trim();
    }

    public String getStandbyServerHost() {
        return standbyServerHost;
    }

    public void setStandbyServerHost(String standbyServerHost) {
        this.standbyServerHost = standbyServerHost == null ? null : standbyServerHost.trim();
    }

    public String getStandbyServerPort() {
        return standbyServerPort;
    }

    public void setStandbyServerPort(String standbyServerPort) {
        this.standbyServerPort = standbyServerPort == null ? null : standbyServerPort.trim();
    }

    public String getStandbyServerConfiguration() {
        return standbyServerConfiguration;
    }

    public void setStandbyServerConfiguration(String standbyServerConfiguration) {
        this.standbyServerConfiguration = standbyServerConfiguration == null ? null : standbyServerConfiguration.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }
    
    public void setInserttime(String inserttime) {
        this.inserttime = DateUtil.parseDate(inserttime,"yyyy-MM-dd HH:mm:ss");
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

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}