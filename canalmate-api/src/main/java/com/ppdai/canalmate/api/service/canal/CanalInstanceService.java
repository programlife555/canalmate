package com.ppdai.canalmate.api.service.canal;


import com.ppdai.canalmate.api.core.ResultCode;
import com.ppdai.canalmate.api.dao.canal.server.TbCanalinstanceConfigMapper;
import com.ppdai.canalmate.api.entity.dto.MenuDto;
import com.ppdai.canalmate.api.entity.dto.ResultResponse;
import com.ppdai.canalmate.api.entity.dto.UserDto;
import com.ppdai.canalmate.api.entity.dto.canal.CanalInstanceConfigDto;
import com.ppdai.canalmate.common.utils.ReponseEnum;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class CanalInstanceService {
    @Autowired
    @Lazy
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlJdbcTemplate;//通过jdbcTemplate的方式操作数据库,可以和下面的mybatis的方式共存
    
    @Autowired
    private TbCanalinstanceConfigMapper tbCanalinstanceConfigMapper;//通过myBatis的方式操作数据库,可以和上面的jdbcTemplate的方式共存
    

    public List<CanalInstanceConfigDto> selectCanalInstanceConfig(Map<String,String> argsMap) {
    	String temp="select instance_name, mysql_slaveId, master_address, master_journal_name, master_position, "
    			+ "master_timestamp, tsdb_enable, tsdb_dir, tsdb_url, tsdb_dbUsername, tsdb_dbPassword, standby_address, "
    			+ "standby_journal_name, standby_position, standby_timestamp, dbUsername, dbPassword, defaultDatabaseName, "
    			+ "connectionCharset, filter_regex, filter_black_regex \r\n" + 
    			"from tb_canalinstance_config where 1=1 and isactive=1 ";
        StringBuffer selectByParamSql = new StringBuffer(temp);
        String instanceName=argsMap.get("instance_name");
        String masterAddress=argsMap.get("master_address");
        if (StringUtils.isNotBlank(instanceName)) {
        	selectByParamSql.append(" and instance_name like '%").append(instanceName).append("%' ");
        }
        if (StringUtils.isNotBlank(masterAddress)) {
        	selectByParamSql.append(" and master_address like '%").append(masterAddress).append("%' ");
        }

        List<CanalInstanceConfigDto> resultList = new ArrayList<CanalInstanceConfigDto>();
        mysqlJdbcTemplate.query(selectByParamSql.toString(), new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
            	CanalInstanceConfigDto dto=new CanalInstanceConfigDto();
            	dto.setInstanceName(rs.getString("instance_name"));
            	dto.setMysqlSlaveId(rs.getString("mysql_slaveId"));
            	dto.setMasterAddress(rs.getString("master_address"));
            	dto.setMasterJournalName(rs.getString("master_journal_name"));
            	dto.setMasterPosition(rs.getString("master_position"));
            	dto.setMasterTimestamp(rs.getString("master_timestamp"));
            	dto.setTsdbEnable(rs.getString("tsdb_enable"));
            	dto.setTsdbDir(rs.getString("tsdb_dir"));
            	dto.setTsdbUrl(rs.getString("tsdb_url"));
            	dto.setTsdbDbUsername(rs.getString("tsdb_dbUsername"));
            	dto.setTsdbDbPassword(rs.getString("tsdb_dbPassword"));
            	dto.setStandbyAddress(rs.getString("standby_address"));
            	dto.setStandbyJournalName(rs.getString("standby_journal_name"));
              	dto.setStandbyPosition(rs.getString("standby_position"));
            	dto.setDbUsername(rs.getString("dbUsername"));
            	dto.setDbPassword(rs.getString("dbPassword"));
            	dto.setDefaultDatabaseName(rs.getString("defaultDatabaseName"));
            	dto.setConnectionCharset(rs.getString("connectionCharset"));
            	dto.setFilterRegex(rs.getString("filter_regex"));
            	dto.setFilterBlackRegex(rs.getString("filter_black_regex"));
            	
                resultList.add(dto);
            }
        });
        return resultList;
    }
    
//    public List<TbCanalinstanceConfig> selectCanalInstanceConfigByMybatis(TbCanalinstanceConfig example) {
//    	List<TbCanalinstanceConfig> instanceConfigList = new ArrayList<TbCanalinstanceConfig>();
//
//    	instanceConfigList=tbCanalinstanceConfigMapper.selectByExample(example);
//    	
//    	return instanceConfigList;
//    }
    
    public ResultResponse insertCanalInstanceConfig(CanalInstanceConfigDto canalInstanceConfig) {
        // 判断是否已注册
//        int count = mysqlJdbcTemplate.queryForObject(selectUserIgnoreActiveByCodeSql, Integer.class, user.getUserCode());
//        if (count != 0) {
//            return new ResultResponse(false, UserReponseEnum.USER_EXIST.getResCode(),
//                    UserReponseEnum.USER_EXIST.getResMsg());
//        }

        //
    	String insertsql="insert into tb_canalinstance_config(instance_name, mysql_slaveId, master_address, dbUsername, dbPassword, filter_regex) values (?,?,?,?,?,?)";
        int i = mysqlJdbcTemplate.update(insertsql, new Object[]{canalInstanceConfig.getInstanceName(), 
        		canalInstanceConfig.getMysqlSlaveId(), canalInstanceConfig.getMasterAddress(),
        		canalInstanceConfig.getDbUsername(), canalInstanceConfig.getDbPassword(), canalInstanceConfig.getFilterRegex()});

        return i == 1 ? new ResultResponse(true, ReponseEnum.SUCCEED.getResCode(), ReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, ReponseEnum.FAIL.getResCode(), ReponseEnum.FAIL.getResMsg());
    }

}
