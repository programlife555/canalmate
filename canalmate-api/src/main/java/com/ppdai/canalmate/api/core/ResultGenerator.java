package com.ppdai.canalmate.api.core;

import com.ppdai.canalmate.common.utils.ReponseEnum;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ReponseEnum.SUCCEED.getResCode())
                .setMessage(ReponseEnum.SUCCEED.getResMsg());
    }
    

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ReponseEnum.SUCCEED.getResCode())
                .setMessage(ReponseEnum.SUCCEED.getResMsg())
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ReponseEnum.FAIL.getResCode())
                .setMessage(message);
    }
}
