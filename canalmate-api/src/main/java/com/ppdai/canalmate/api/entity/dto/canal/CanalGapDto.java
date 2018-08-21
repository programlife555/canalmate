package com.ppdai.canalmate.api.entity.dto.canal;

import java.util.Date;

import com.ppdai.canalmate.api.entity.dto.BaseDto;

/**
 * canal实例Dto
 *
 */
public class CanalGapDto extends BaseDto {

    private static final long serialVersionUID = -3850611166236971181L;
    
    private String destnationName;
    private String mysqlSlaveId;//包括了canal destination的master的slaveid和standby的slaveid
    private String masterAddress;
    private String masterJournalName;
    private String masterPosition;
    private String instanceJournalName;
    private String instancePosition;
    private String instanceTimestamp;//unix时间戳
    private String instanceTimestampStr;//unix时间戳对应的yyyymmdd日期
    private String positionGap;
    private String comment;
    private String colour;

    public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}

    
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDestnationName() {
		return destnationName;
	}
	public void setDestnationName(String destnationName) {
		this.destnationName = destnationName;
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
	public String getInstanceJournalName() {
		return instanceJournalName;
	}
	public void setInstanceJournalName(String instanceJournalName) {
		this.instanceJournalName = instanceJournalName;
	}
	public String getInstancePosition() {
		return instancePosition;
	}
	public void setInstancePosition(String instancePosition) {
		this.instancePosition = instancePosition;
	}
	public String getInstanceTimestamp() {
		return instanceTimestamp;
	}
	public void setInstanceTimestamp(String instanceTimestamp) {
		this.instanceTimestamp = instanceTimestamp;
	}
	public String getInstanceTimestampStr() {
		return instanceTimestampStr;
	}
	public void setInstanceTimestampStr(String instanceTimestampStr) {
		this.instanceTimestampStr = instanceTimestampStr;
	}
	public String getPositionGap() {
		return positionGap;
	}
	public void setPositionGap(String positionGap) {
		this.positionGap = positionGap;
	}
	
	
    
   
}
