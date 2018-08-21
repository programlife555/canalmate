package com.ppdai.canalmate.common.utils;

/**
 * 
 * @author yanxd
 */
public enum ReponseEnum {

	SUCCEED("1", "操作成功"), FAIL("0", "操作失败"), USER_EXIST("002", "用户已存在"), USER_NOT_EXIST("003",
			"用户不存在或已删除"), USER_PWD_ERROR("004", "用户密码输入错误"), AUTH_EXIST("102", "权限已存在"), USER_AUTH_EXIST("202",
					"用户此权限已存在"), MENU_EXIST("302", "菜单已存在"), MENU_AUTH_EXIST("402", "菜单权限已存在"),
    UNAUTHORIZED("401","未认证（签名错误）"),//未认证（签名错误）
    NOT_FOUND("404","接口不存在"),//接口不存在
    INTERNAL_SERVER_ERROR("500","服务器内部错误");//服务器内部错误

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

	private ReponseEnum(String resCode, String resMsg) {
		this.resCode = resCode;
		this.resMsg = resMsg;
	}

}
