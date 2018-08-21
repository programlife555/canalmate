package com.ppdai.canalmate.api.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ppdai.canalmate.api.core.BaseController;
import com.ppdai.canalmate.api.core.Result;
import com.ppdai.canalmate.api.core.ResultCode;
import com.ppdai.canalmate.api.model.canal.server.DestinationsConfig;
import com.ppdai.canalmate.api.service.canal.CanalGapService;
import com.ppdai.canalmate.common.utils.CanalZKUtils;
import com.ppdai.canalmate.common.utils.P;
import com.ppdai.canalmate.common.utils.ReponseEnum;
import com.ppdai.canalmate.common.utils.ServiceUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;



@Api(value = "/", description = "默认请求根路径/的操作")
@RestController
public class HealthCheck extends BaseController {
	
	static {
		System.out.println("==========加载HealthCheck===========");
	}

	@Autowired
    @Qualifier("mysqlJdbcTemplate")
    protected JdbcTemplate mysqlJdbcTemplate;
	
    @Qualifier(value = "canalGapService")
    @Autowired
    CanalGapService canalGapService;
	
	@RequestMapping(value = "/hs", method = RequestMethod.GET)
    public void healthCheck(HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();//获取OutputStream输出流
        response.setHeader( "content-type", "text/html;charset=UTF-8" );
        byte[] dataByteArr = "OK".getBytes( "UTF-8" );//将字符转换成字节数组，指定以UTF-8编码进行转换
        logger.info( "healthCheck 接口被调用一次,return OK" );
        outputStream.write( dataByteArr );//使用OutputStream流向客户端输出字节数组
    }
	
	@ApiOperation(value="test测试", notes="")
    @RequestMapping(value = "/test操作", method = RequestMethod.GET)
	public Result testProperties() {
		
		String update="update tb_user_role t2 set role_code='1' where t2.user_code = 'admin'";
		int i = mysqlJdbcTemplate.update(update, new Object[]{});
    	
        Result result = new Result();
        result.setCode(ReponseEnum.SUCCEED.getResCode());
        result.setMessage(ReponseEnum.SUCCEED.getResMsg());
        result.setData(i);
		return result;
	}
	
	@ApiOperation(value="更新属性表的邮件标识,1:发送，0:不发送", notes="")
    @RequestMapping(value = "/updateSendMail", method = RequestMethod.GET)
	public Result updateSendMail(@ApiParam(required = true, value = "更新属性表的邮件标识,1:发送，0:不发送") @RequestParam(value = "config_value", required=true) String configValue) {
		P.p("=============config_value:"+configValue);
		String update="update tb_config t2 set config_value=? where t2.config_name = 'is_send_mail'";
		int i = mysqlJdbcTemplate.update(update, new Object[]{configValue});
    	
        Result result = new Result();
        result.setCode(ReponseEnum.SUCCEED.getResCode());
        result.setMessage(ReponseEnum.SUCCEED.getResMsg());
        result.setData(i);
		return result;
	}
	
	@ApiOperation(value="/home swagger注释", notes="/home swagger注释_notes")
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public Map<String, Object> home() {
        return ServiceUtil.returnSuccess();
    }
	
	
	
}
