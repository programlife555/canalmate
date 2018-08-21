package com.ppdai.canalmate.api.core;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 */
public class PageResult extends Result {
    private String totalNum;



    public String getTotalNum() {
		return totalNum;
	}


	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}


	@Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
