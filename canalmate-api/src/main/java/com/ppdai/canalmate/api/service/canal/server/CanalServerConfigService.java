package com.ppdai.canalmate.api.service.canal.server;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.canalmate.api.model.canal.server.CanalServerConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


public interface CanalServerConfigService {

    /**
     * @Author: 黑皮大野猪
     * @Description: 插入canal server配置信息
     * @Date: 2018/5/18
     * @param canalServerConfig
     */
    void addCanalServer(CanalServerConfig canalServerConfig);

    /**
     * @Author: 黑皮大野猪
     * @Description: 插入canal server配置信息
     * @Date: 2018/5/21
     * @param
     */
    List<JSONObject> listCanalMenu();

    /**
     * @Author: 黑皮大野猪
     * @Description: 删除canal server配置信息
     * @Date: 2018/5/22
     * @param canalServerName
     */
    void deleteCanalServer(String canalServerName);

    void updateCanalServer(CanalServerConfig canalServerConfig);

    Map<String,Object> selectCanalServerOldConfig(String canalServerName);

    boolean deployCanalServer(String canalServerName) throws IOException;

    Map<String,Object> startCanalServer(String canalServerName,String type) throws IOException;

    Map<String,Object> stopCanalServer(String canalServerName,String type) throws IOException;

}
