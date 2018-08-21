package com.ppdai.canalmate.api.service.canal;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.ppdai.canalmate.api.model.canal.server.ClientConfig;
import com.ppdai.canalmate.api.model.canal.server.DestinationsConfig;
import com.ppdai.canalmate.common.utils.P;


@Component
public class JdbcTemplateService {
    @Autowired
    @Lazy
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlJdbcTemplate;//通过jdbcTemplate的方式操作数据库,可以和下面的mybatis的方式共存
    

    public ClientConfig selectClientConfigByArgs(Map<String,String> argsMap) {
    	String temp="select id, destination_id, path, host, client_name, "
    			+ "zk_servers, filter, inserttime,updatetime,isactive \r\n" + 
    			"from client_config where 1=1 and isactive=1 ";
        StringBuffer selectByParamSql = new StringBuffer(temp);
        String id=argsMap.get("id");
        if (StringUtils.isNotBlank(id)) {
        	selectByParamSql.append(" and id = ").append(Integer.parseInt(id)).append(" ");
        }
        String destinationId=argsMap.get("destination_id");
        if (StringUtils.isNotBlank(destinationId)) {
        	selectByParamSql.append(" and destination_id = ").append(Integer.parseInt(destinationId)).append(" ");
        }
//        P.p(selectByParamSql.toString());
        ClientConfig clientConfig=null;
        try {
        	clientConfig=(ClientConfig)mysqlJdbcTemplate.queryForObject(selectByParamSql.toString(),
        			new BeanPropertyRowMapper(ClientConfig.class));
        }catch (Exception e) {
        	System.out.println("clientConfig 个数不止1个，或者0个，会报错");
//        	e.printStackTrace();
        }
        
        return clientConfig;
    }
    
    
    
}
