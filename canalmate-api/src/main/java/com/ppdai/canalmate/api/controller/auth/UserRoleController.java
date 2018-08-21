package com.ppdai.canalmate.api.controller.auth;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ppdai.canalmate.api.core.Result;
import com.ppdai.canalmate.api.dataaccess.auth.UserRoleDataAccess;
import com.ppdai.canalmate.api.entity.dto.ResultResponse;
import com.ppdai.canalmate.api.entity.dto.RoleReponseEnum;
import com.ppdai.canalmate.api.entity.dto.UserRoleDto;

import io.swagger.annotations.ApiOperation;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gongyun on 2017/11/21.
 */
@RestController
@EnableAutoConfiguration
public class UserRoleController {

    @Autowired
    UserRoleDataAccess userRoleDataAccess;
    private static Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    // 新增(角色和用户匹配)
    @ApiOperation(value = "根据查询条件，列出用户列表",httpMethod ="GET", response = Result.class)
    @RequestMapping(value = "/userRole/insert", method = RequestMethod.POST)
    public ResultResponse inserUserRole(@ModelAttribute UserRoleDto userRoleDto, HttpServletRequest request) {
        if (userRoleDto == null || StringUtils.isBlank(userRoleDto.getUserCode()) || StringUtils.isBlank(userRoleDto.getRoleCode())) {
            return new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), "用户编号、角色编号非空");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("userCode".equals(cookie.getName())) {
                    String userCode = cookie.getValue();
                    userRoleDto.setCreateUser(userCode);
                    break;
                }
            }
        }
        return userRoleDataAccess.insertUserRole(userRoleDto);
    }

    // 删除(角色和用户的匹配)
    @ApiOperation(value = "根据PkUserRoleId，删除角色和用户的匹配",httpMethod ="POST", response = ResultResponse.class)
    @RequestMapping(value = "/userRole/delete", method = RequestMethod.POST)
    ResultResponse deleteUserRole(UserRoleDto userRoleDto, HttpServletRequest request) {
        if (userRoleDto == null || userRoleDto.getPkUserRoleId() == null) {
            return new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), "主键非空");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("userCode".equals(cookie.getName())) {
                    String userCode = cookie.getValue();
                    userRoleDto.setUpdateUser(userCode);
                    break;
                }
            }
        }
        return userRoleDataAccess.deleteUserRole(userRoleDto);
    }

    // 删除(角色)
    @RequestMapping(value = "/userRole/deleteByIds", method = RequestMethod.POST)
    ResultResponse deleteByIds(@RequestParam("ids[]") String[] ids, HttpServletRequest request) {
        if (ids == null || !(ids.length > 0)) {
            return new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), "主键非空");
        }
        String userCode = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("userCode".equals(cookie.getName())) {
                    userCode = cookie.getValue();
                    break;
                }
            }
        }
        return userRoleDataAccess.deleteByIds(ids, userCode);
    }


    // open
    
    @RequestMapping(value = "/userRole/openByIds", method = RequestMethod.POST)
    ResultResponse openByIds(@RequestParam("ids[]") String[] ids, HttpServletRequest request) {
        if (ids == null || !(ids.length > 0)) {
            return new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), "主键非空");
        }
        String userCode = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("userCode".equals(cookie.getName())) {
                    userCode = cookie.getValue();
                    break;
                }
            }
        }
        return userRoleDataAccess.openByIds(ids, userCode);
    }


    // 更新用户角色
    @RequestMapping(value = "/userRole/update", method = RequestMethod.POST)
    ResultResponse updateUserRole(UserRoleDto userRoleDto, HttpServletRequest request) {
        if (userRoleDto == null || userRoleDto.getPkUserRoleId() == null || userRoleDto.getIsActive() == null) {
            return new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), "逻辑删除及主键非空");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("userCode".equals(cookie.getName())) {
                    String userCode = cookie.getValue();
                    userRoleDto.setUpdateUser(userCode);
                    break;
                }
            }
        }
        return userRoleDataAccess.updateUserRole(userRoleDto);
    }

    // 查询LIST(用户、权限)
    @ApiOperation(value = "根据userRoleDto，列出该用户的角色和用户的匹配",httpMethod ="POST")
    @RequestMapping(value = "/userRole/selectRole", method = RequestMethod.POST)
    List<UserRoleDto> selectUserRole(UserRoleDto userRoleDto) {
        if (userRoleDto == null) {
            userRoleDto = new UserRoleDto();
        }
        return userRoleDataAccess.selectRole(userRoleDto);
    }
}
