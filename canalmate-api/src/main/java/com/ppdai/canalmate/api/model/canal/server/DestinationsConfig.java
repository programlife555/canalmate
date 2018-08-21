package com.ppdai.canalmate.api.model.canal.server;

import java.util.Date;

public class DestinationsConfig {
    private Long id;

    private Long canalId;

    private String destinationName;

    private String description;

    private String destinationConfiguration;

    private String standbyConfiguration;

    private Date inserttime;

    private Date updatetime;

    private Boolean isactive;

    private String canalServerName;

    private  String  destinationNameUpdateKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCanalId() {
        return canalId;
    }

    public void setCanalId(Long canalId) {
        this.canalId = canalId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName == null ? null : destinationName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDestinationConfiguration() {
        return destinationConfiguration;
    }

    public void setDestinationConfiguration(String destinationConfiguration) {
        this.destinationConfiguration = destinationConfiguration == null ? null : destinationConfiguration.trim();
    }

    public String getStandbyConfiguration() {
        return standbyConfiguration;
    }

    public void setStandbyConfiguration(String standbyConfiguration) {
        this.standbyConfiguration = standbyConfiguration == null ? null : standbyConfiguration.trim();
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

    public String getCanalServerName() {
        return canalServerName;
    }

    public void setCanalServerName(String canalServerName) {
        this.canalServerName = canalServerName;
    }

    public String getDestinationNameUpdateKey() {
        return destinationNameUpdateKey;
    }

    public void setDestinationNameUpdateKey(String destinationNameUpdateKey) {
        this.destinationNameUpdateKey = destinationNameUpdateKey;
    }

}