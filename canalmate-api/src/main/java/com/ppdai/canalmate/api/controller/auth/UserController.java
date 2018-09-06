package com.ppdai.canalmate.api.controller.auth;

import com.ppdai.canalmate.api.core.BaseController;
import com.ppdai.canalmate.api.core.Result;
import com.ppdai.canalmate.api.core.ResultCode;
import com.ppdai.canalmate.api.dataaccess.auth.UserDataAccess;
import com.ppdai.canalmate.api.entity.dto.LoginResponse;
import com.ppdai.canalmate.api.entity.dto.MenuDto;
import com.ppdai.canalmate.api.entity.dto.ResultResponse;
import com.ppdai.canalmate.api.entity.dto.UserDto;
import com.ppdai.canalmate.api.entity.dto.institute.InstitutionReponseEnum;
import com.ppdai.canalmate.common.cons.CanalConstants;
import com.ppdai.canalmate.common.utils.MD5Encrypt;
import com.ppdai.canalmate.common.utils.P;
import com.ppdai.canalmate.common.utils.ReponseEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户Controller
 */
@Api(value = "UserController", description = "用户,菜单相关的操作")
@RestController
@EnableAutoConfiguration
public class UserController extends BaseController{

    @Qualifier(value = "userDataAccess")
    @Autowired
    UserDataAccess userDataAccess;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    // 添加
    @ApiOperation(value = "用户新增的接口",httpMethod ="POST", response = ResultResponse.class)
    @RequestMapping(value = "/user/insert", method = RequestMethod.POST)
    public ResultResponse insertUser(@ModelAttribute UserDto userDto) {
        if (userDto == null || StringUtils.isBlank(userDto.getUserCode()) || StringUtils.isBlank(userDto.getUserName()) || StringUtils.isBlank(userDto.getUserPassword())) {
            return new ResultResponse(false, ReponseEnum.FAIL.getResCode(), "用户编号、用户名称、用户密码非空");
        }
        userDto.setUserPassword(MD5Encrypt.generatePassword(userDto.getUserPassword() + CanalConstants.PWD_postfix));
        return userDataAccess.insertUser(userDto);
    }

    // 修改
    @ApiOperation(value = "用户修改的接口",httpMethod ="POST", response = ResultResponse.class)
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ResultResponse updateUser(@ModelAttribute UserDto userDto) {
        if (userDto == null) {
            return new ResultResponse(false, ReponseEnum.FAIL.getResCode(), "修改对象非空");
        }
        if (StringUtils.isNotBlank(userDto.getUserPassword())) {
            userDto.setUserPassword(MD5Encrypt.generatePassword(userDto.getUserPassword() + CanalConstants.PWD_postfix));
        }
        return userDataAccess.updateUser(userDto);
    }

    // 删除
    @ApiOperation(value = "用户删除的接口，根据用户的编号删除",httpMethod ="POST", response = ResultResponse.class)
    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public ResultResponse deleteUser(@ModelAttribute UserDto user) {
        if (user == null || user.getPkUserId() == null) {
            return new ResultResponse(false, ReponseEnum.FAIL.getResCode(), "主键非空");
        }
        return userDataAccess.deleteUser(user);
    }

    // 删除
//    @RequestMapping(value = "/user/deleteByIds", method = RequestMethod.POST)
//    ResultResponse deleteByIds(@RequestParam("ids[]") String[] ids) {
//        if (ids == null || !(ids.length > 0)) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "主键非空");
//        }
//        return userDataAccess.deleteByIds(ids);
//    }
//
//    //批量打开
//    @RequestMapping(value = "/user/openByIds", method = RequestMethod.POST)
//    ResultResponse openByIds(@RequestParam("ids[]") String[] ids) {
//        if (ids == null || !(ids.length > 0)) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "主键非空");
//        }
//        return userDataAccess.openByIds(ids);
//    }
//
//    // 用户LIST
//    @RequestMapping(value = "/user/selectUserByParam", method = RequestMethod.POST)
//    List<UserDto> selectUserByParam(UserDto user) {
//        if (user == null) {
//            user = new UserDto();
//        }
//        return userDataAccess.selectUserByParam(user);
//    }

    // 用户LIST
    @CrossOrigin
    @ApiOperation(value = "根据查询条件，列出用户列表",httpMethod ="GET", response = Result.class)
    @RequestMapping(value = "/user/selectUserByParam", method = RequestMethod.GET)
    public Result  selectUserByParamq(@ModelAttribute UserDto user) {
        if (user == null) {
            user = new UserDto();
        }
        List<UserDto> userList=userDataAccess.selectUserByParam(user);
        
	   	 Result result = new Result();
	     result.setCode(ReponseEnum.SUCCEED.getResCode());
	     result.setMessage(ReponseEnum.SUCCEED.getResMsg());
	     result.setData(userList);

       return result;
    }


    // 查询用户的菜单权限
    @ApiOperation(value = "根据用户名，查询用户拥有的菜单权限",httpMethod ="GET", response = Result.class)
    @RequestMapping(value = "/user/selectUserMenuAuth", method = RequestMethod.GET)
    Result selectUserMenuAuth(@RequestParam(value = "userCode") String userCode) {
    	
    	List<MenuDto> menuList = new ArrayList<MenuDto>();
    	boolean isAdmin=userDataAccess.isAdmin(userCode);
    	String adminRoleCode="1";//管理员角色ID，与数据库值关联
    	String notAdminRoleCode="2";//普通用户角色ID，与数据库关联
		
    	if(isAdmin) {
    		menuList = userDataAccess.selectUserMenuAuth(adminRoleCode);
    	}else {
    		menuList = userDataAccess.selectUserMenuAuth(notAdminRoleCode);
    	}
    	
        Result result = new Result();
        result.setCode(ReponseEnum.SUCCEED.getResCode());
        result.setMessage(ReponseEnum.SUCCEED.getResMsg());
        result.setData(menuList);
        return result;
    }


    // 登录
    @ApiOperation(value = "根据用户名密码，查询用户信息",httpMethod ="POST", response = ResultResponse.class)
    @RequestMapping(value = "/user/logon2", method = RequestMethod.POST)
    public ResultResponse userLogin(HttpServletRequest request, HttpServletResponse response,
    		@ApiParam(name = "userCode", required = true, value = "用户名") @RequestParam(value = "userCode") String userCode,
    		@ApiParam(name = "userPassword", required = true, value = "用户密码") @RequestParam(value = "userPassword") String userPassword) {
    	//ResultResponse resultResponse = new ResultResponse();
    	LoginResponse resultResponse = new LoginResponse();
        resultResponse = userDataAccess.login(userCode, MD5Encrypt.generatePassword(userPassword + CanalConstants.PWD_postfix));
        
        if (resultResponse == null || !resultResponse.isSucceed()) {
            return new ResultResponse(false, ReponseEnum.FAIL.getResCode(), "用户名或密码输入错误！");
        } else {
        	P.p("=================发布对应的版本时间：20180906");
            // 登录成功,加密,签名
            try {
                Cookie codeCookie = new Cookie("userCode", URLEncoder.encode(userCode, "UTF-8"));
                codeCookie.setPath("/");
                codeCookie.setMaxAge(30 * 60);
                Cookie passwordCookie = new Cookie("userPassword", MD5Encrypt.generatePassword(userPassword + CanalConstants.PWD_postfix));
                passwordCookie.setPath("/");
                passwordCookie.setMaxAge(30 * 60);
                request.getSession().setMaxInactiveInterval(30 * 60);
                response.addCookie(codeCookie);
                response.addCookie(passwordCookie);

                resultResponse.setSucceed(true);
                resultResponse.setReCode("000");
                resultResponse.setRsMsg("登录成功！");
            } catch (UnsupportedEncodingException e) {
                logger.error("登录，用户编号转码失败:" + userCode, e);
                return new ResultResponse(false, ReponseEnum.FAIL.getResCode(), "登录用户编号转码失败:" + userCode);
            }
        }
        return resultResponse;
    }

}
