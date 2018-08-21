package com.ppdai.canalmate.api.entity.dto.institute;

/**
 * @author yanxd
 */
public enum InstitutionReponseEnum {

    SUCCEED("000", "操作成功"), FAIL("001", "操作失败"), INST_METRICS_EXIST("002", "机构事件和Metrics已存在"),
    VERSION_NOTVALID("003", "获取机构版本失败"), INST_INSERT_ERROR("004", "新增机构失败"), INST_DELETE_ERROR("005", "机构删除失败"),
    INST_INFO_EXIST("006", "机构信息已存在"), INST_NOT_EXIST("007", "修改的机构信息不存在"), INST_METRICS_NOT_EXIST("007", "修改的机构事件和Metrics信息不存在");


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

    private InstitutionReponseEnum(String resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

}
