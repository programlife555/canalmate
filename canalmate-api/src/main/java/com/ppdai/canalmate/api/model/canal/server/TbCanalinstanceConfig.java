package com.ppdai.canalmate.api.model.canal.server;

import java.util.Date;

public class TbCanalinstanceConfig {
	private Integer pkId;

    private String instanceName;

    private String mysqlSlaveid;

    private String masterAddress;

    private String masterJournalName;

    private String masterPosition;

    private String masterTimestamp;

    private String tsdbEnable;

    private String tsdbDir;

    private String tsdbUrl;

    private String tsdbDbusername;

    private String tsdbDbpassword;

    private String standbyAddress;

    private String standbyJournalName;

    private String standbyPosition;

    private String standbyTimestamp;

    private String dbusername;

    private String dbpassword;

    private String defaultdatabasename;

    private String connectioncharset;

    private String filterRegex;

    private String filterBlackRegex;

    private Integer fkServerConfigId;

    private Date inserttime;

    private Date updatetime;

    private Boolean isactive;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName == null ? null : instanceName.trim();
    }

    public String getMysqlSlaveid() {
        return mysqlSlaveid;
    }

    public void setMysqlSlaveid(String mysqlSlaveid) {
        this.mysqlSlaveid = mysqlSlaveid == null ? null : mysqlSlaveid.trim();
    }

    public String getMasterAddress() {
        return masterAddress;
    }

    public void setMasterAddress(String masterAddress) {
        this.masterAddress = masterAddress == null ? null : masterAddress.trim();
    }

    public String getMasterJournalName() {
        return masterJournalName;
    }

    public void setMasterJournalName(String masterJournalName) {
        this.masterJournalName = masterJournalName == null ? null : masterJournalName.trim();
    }

    public String getMasterPosition() {
        return masterPosition;
    }

    public void setMasterPosition(String masterPosition) {
        this.masterPosition = masterPosition == null ? null : masterPosition.trim();
    }

    public String getMasterTimestamp() {
        return masterTimestamp;
    }

    public void setMasterTimestamp(String masterTimestamp) {
        this.masterTimestamp = masterTimestamp == null ? null : masterTimestamp.trim();
    }

    public String getTsdbEnable() {
        return tsdbEnable;
    }

    public void setTsdbEnable(String tsdbEnable) {
        this.tsdbEnable = tsdbEnable == null ? null : tsdbEnable.trim();
    }

    public String getTsdbDir() {
        return tsdbDir;
    }

    public void setTsdbDir(String tsdbDir) {
        this.tsdbDir = tsdbDir == null ? null : tsdbDir.trim();
    }

    public String getTsdbUrl() {
        return tsdbUrl;
    }

    public void setTsdbUrl(String tsdbUrl) {
        this.tsdbUrl = tsdbUrl == null ? null : tsdbUrl.trim();
    }

    public String getTsdbDbusername() {
        return tsdbDbusername;
    }

    public void setTsdbDbusername(String tsdbDbusername) {
        this.tsdbDbusername = tsdbDbusername == null ? null : tsdbDbusername.trim();
    }

    public String getTsdbDbpassword() {
        return tsdbDbpassword;
    }

    public void setTsdbDbpassword(String tsdbDbpassword) {
        this.tsdbDbpassword = tsdbDbpassword == null ? null : tsdbDbpassword.trim();
    }

    public String getStandbyAddress() {
        return standbyAddress;
    }

    public void setStandbyAddress(String standbyAddress) {
        this.standbyAddress = standbyAddress == null ? null : standbyAddress.trim();
    }

    public String getStandbyJournalName() {
        return standbyJournalName;
    }

    public void setStandbyJournalName(String standbyJournalName) {
        this.standbyJournalName = standbyJournalName == null ? null : standbyJournalName.trim();
    }

    public String getStandbyPosition() {
        return standbyPosition;
    }

    public void setStandbyPosition(String standbyPosition) {
        this.standbyPosition = standbyPosition == null ? null : standbyPosition.trim();
    }

    public String getStandbyTimestamp() {
        return standbyTimestamp;
    }

    public void setStandbyTimestamp(String standbyTimestamp) {
        this.standbyTimestamp = standbyTimestamp == null ? null : standbyTimestamp.trim();
    }

    public String getDbusername() {
        return dbusername;
    }

    public void setDbusername(String dbusername) {
        this.dbusername = dbusername == null ? null : dbusername.trim();
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword == null ? null : dbpassword.trim();
    }

    public String getDefaultdatabasename() {
        return defaultdatabasename;
    }

    public void setDefaultdatabasename(String defaultdatabasename) {
        this.defaultdatabasename = defaultdatabasename == null ? null : defaultdatabasename.trim();
    }

    public String getConnectioncharset() {
        return connectioncharset;
    }

    public void setConnectioncharset(String connectioncharset) {
        this.connectioncharset = connectioncharset == null ? null : connectioncharset.trim();
    }

    public String getFilterRegex() {
        return filterRegex;
    }

    public void setFilterRegex(String filterRegex) {
        this.filterRegex = filterRegex == null ? null : filterRegex.trim();
    }

    public String getFilterBlackRegex() {
        return filterBlackRegex;
    }

    public void setFilterBlackRegex(String filterBlackRegex) {
        this.filterBlackRegex = filterBlackRegex == null ? null : filterBlackRegex.trim();
    }

    public Integer getFkServerConfigId() {
        return fkServerConfigId;
    }

    public void setFkServerConfigId(Integer fkServerConfigId) {
        this.fkServerConfigId = fkServerConfigId;
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