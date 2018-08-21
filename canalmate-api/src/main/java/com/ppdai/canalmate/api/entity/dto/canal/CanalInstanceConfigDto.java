package com.ppdai.canalmate.api.entity.dto.canal;

import java.util.Date;

import com.ppdai.canalmate.api.entity.dto.BaseDto;

/**
 * canal实例Dto
 *
 */
public class CanalInstanceConfigDto extends BaseDto {

    /**
     *
     */
    private static final long serialVersionUID = -3850611166236971181L;
    
    private String instanceName;
    private String mysqlSlaveId;
    private String masterAddress;
    private String masterJournalName;
    private String masterPosition;
    private String masterTimestamp;
    private String tsdbEnable;
    private String tsdbDir;
    private String tsdbUrl;
    private String tsdbDbUsername;
    private String tsdbDbPassword;
    private String standbyAddress;
    private String standbyJournalName;
    private String standbyPosition;
    private String standbyTimestamp;
    private String dbUsername;
    private String dbPassword;
    private String defaultDatabaseName;
    private String connectionCharset;
    private String filterRegex;
    private String filterBlackRegex;
    private String insertTime;
    private String updateTime;
    private Byte isActive;
    
    
	public String getDbUsername() {
		return dbUsername;
	}
	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getDefaultDatabaseName() {
		return defaultDatabaseName;
	}
	public void setDefaultDatabaseName(String defaultDatabaseName) {
		this.defaultDatabaseName = defaultDatabaseName;
	}
	public String getConnectionCharset() {
		return connectionCharset;
	}
	public void setConnectionCharset(String connectionCharset) {
		this.connectionCharset = connectionCharset;
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
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getMysqlSlaveId() {
		return mysqlSlaveId;
	}
	public void setMysqlSlaveId(String mysqlSlaveId) {
		this.mysqlSlaveId = mysqlSlaveId;
	}
	public String getMasterAddress() {
		return masterAddress;
	}
	public void setMasterAddress(String masterAddress) {
		this.masterAddress = masterAddress;
	}
	public String getMasterJournalName() {
		return masterJournalName;
	}
	public void setMasterJournalName(String masterJournalName) {
		this.masterJournalName = masterJournalName;
	}
	public String getMasterPosition() {
		return masterPosition;
	}
	public void setMasterPosition(String masterPosition) {
		this.masterPosition = masterPosition;
	}
	public String getMasterTimestamp() {
		return masterTimestamp;
	}
	public void setMasterTimestamp(String masterTimestamp) {
		this.masterTimestamp = masterTimestamp;
	}
	public String getTsdbEnable() {
		return tsdbEnable;
	}
	public void setTsdbEnable(String tsdbEnable) {
		this.tsdbEnable = tsdbEnable;
	}
	public String getTsdbDir() {
		return tsdbDir;
	}
	public void setTsdbDir(String tsdbDir) {
		this.tsdbDir = tsdbDir;
	}
	public String getTsdbUrl() {
		return tsdbUrl;
	}
	public void setTsdbUrl(String tsdbUrl) {
		this.tsdbUrl = tsdbUrl;
	}
	public String getTsdbDbUsername() {
		return tsdbDbUsername;
	}
	public void setTsdbDbUsername(String tsdbDbUsername) {
		this.tsdbDbUsername = tsdbDbUsername;
	}
	public String getTsdbDbPassword() {
		return tsdbDbPassword;
	}
	public void setTsdbDbPassword(String tsdbDbPassword) {
		this.tsdbDbPassword = tsdbDbPassword;
	}
	public String getStandbyAddress() {
		return standbyAddress;
	}
	public void setStandbyAddress(String standbyAddress) {
		this.standbyAddress = standbyAddress;
	}
	public String getStandbyJournalName() {
		return standbyJournalName;
	}
	public void setStandbyJournalName(String standbyJournalName) {
		this.standbyJournalName = standbyJournalName;
	}
	public String getStandbyPosition() {
		return standbyPosition;
	}
	public void setStandbyPosition(String standbyPosition) {
		this.standbyPosition = standbyPosition;
	}
	public String getStandbyTimestamp() {
		return standbyTimestamp;
	}
	public void setStandbyTimestamp(String standbyTimestamp) {
		this.standbyTimestamp = standbyTimestamp;
	}
	public String getFilterRegex() {
		return filterRegex;
	}
	public void setFilterRegex(String filterRegex) {
		this.filterRegex = filterRegex;
	}
	public String getFilterBlackRegex() {
		return filterBlackRegex;
	}
	public void setFilterBlackRegex(String filterBlackRegex) {
		this.filterBlackRegex = filterBlackRegex;
	}
    
   
}
