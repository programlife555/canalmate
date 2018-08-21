package com.ppdai.canalmate.api.controller.auth;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ppdai.canalmate.api.dataaccess.auth.RoleDataAccess;
import com.ppdai.canalmate.api.entity.dto.ResultResponse;
import com.ppdai.canalmate.api.entity.dto.RoleDto;
import com.ppdai.canalmate.api.entity.dto.RoleReponseEnum;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gongyun on 2017/11/21.
 */
@RestController
@EnableAutoConfiguration
public class RoleController {

    @Autowired
    RoleDataAccess roleDataAccess;
    private static Logger logger = LoggerFactory.getLogger(RoleController.class);


    // 新增(角色)
//    @RequestMapping(value = "/role/insert", method = RequestMethod.POST)
//    ResultResponse insertrole(RoleDto roleDto, HttpServletRequest request) {
//        if (roleDto == null || StringUtils.isBlank(roleDto.getRoleCode()) || StringUtils.isBlank(roleDto.getRoleCode())) {
//            return new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), "告警业务编号、告警编号非空");
//        }
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length > 0) {
//            for (Cookie cookie : cookies) {
//                if ("userCode".equals(cookie.getName())) {
//                    String userCode = cookie.getValue();
//                    roleDto.setCreateUser(userCode);
//                    break;
//                }
//            }
//        }
//        return roleDataAccess.insertRole(roleDto);
//    }

    // 删除(角色)
    @RequestMapping(value = "/role/delete", method = RequestMethod.POST)
    ResultResponse deleteRole(RoleDto roleDto, HttpServletRequest request) {
        if (roleDto == null || roleDto.getPkRoleId() == null) {
            return new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), "主键非空");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("userCode".equals(cookie.getName())) {
                    String userCode = cookie.getValue();
                    roleDto.setUpdateUser(userCode);
                    break;
                }
            }
        }
        return roleDataAccess.deleteRole(roleDto);
    }

    // 删除(角色)
    @RequestMapping(value = "/role/deleteByIds", method = RequestMethod.POST)
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
        return roleDataAccess.deleteByIds(ids, userCode);
    }


    // open
    @RequestMapping(value = "/role/openByIds", method = RequestMethod.POST)
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
        return roleDataAccess.openByIds(ids, userCode);
    }


    // 更新用户角色
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    ResultResponse updateRole(RoleDto roleDto, HttpServletRequest request) {
        if (roleDto == null || roleDto.getPkRoleId() == null || roleDto.getIsActive() == null) {
            return new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), "逻辑删除及主键非空");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("userCode".equals(cookie.getName())) {
                    String userCode = cookie.getValue();
                    roleDto.setUpdateUser(userCode);
                    break;
                }
            }
        }
        return roleDataAccess.updateRole(roleDto);
    }

    // 查询LIST(用户、权限)
    @RequestMapping(value = "/role/selectRole", method = RequestMethod.POST)
    List<RoleDto> selectRole(RoleDto roleDto) {
        if (roleDto == null) {
            roleDto = new RoleDto();
        }
        return roleDataAccess.selectRole(roleDto);
    }
}
