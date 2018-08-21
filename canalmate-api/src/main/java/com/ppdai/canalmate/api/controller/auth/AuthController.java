package com.ppdai.canalmate.api.controller.auth;
//package com.ppdai.console.api.controller.auth;
//
//import com.ppdai.console.api.dataaccess.auth.AuthDataAccess;
//import com.ppdai.console.api.entity.dto.AuthDto;
//import com.ppdai.console.api.entity.dto.ResultResponse;
//import com.ppdai.console.api.entity.dto.UserReponseEnum;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * 权限Controller
// *
// * @author yanxd
// */
//@RestController
//@EnableAutoConfiguration
//public class AuthController {
//
//    @Qualifier(value = "authDataAccess")
//    @Autowired
//    AuthDataAccess authDataAccess;
//
//    // 新增
//    @RequestMapping(value = "/auth/insert", method = RequestMethod.POST)
//    ResultResponse insertAuth(AuthDto authDto) {
//        if (authDto == null || StringUtils.isBlank(authDto.getAuthCode()) || StringUtils.isBlank(authDto.getAuthName())) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "权限编号、权限名称非空");
//        }
//        return authDataAccess.insertAuth(authDto);
//    }
//
//    // 删除
//    @RequestMapping(value = "/auth/delete", method = RequestMethod.POST)
//    ResultResponse deleteAuth(AuthDto authDto) {
//        if (authDto == null || authDto.getPkAuthId() == null) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "主键非空");
//        }
//        return authDataAccess.deleteAuth(authDto);
//    }
//
//    @RequestMapping(value = "/auth/deleteByIds", method = RequestMethod.POST)
//    ResultResponse deleteAuthByIds(@RequestParam("ids[]") String[] ids) {
//        if (ids == null || !(ids.length > 0)) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "主键非空");
//        }
//        return authDataAccess.deleteByIds(ids);
//    }
//    @RequestMapping(value = "/auth/openByIds", method = RequestMethod.POST)
//    ResultResponse openByIds(@RequestParam("ids[]") String[] ids) {
//        if (ids == null || !(ids.length > 0)) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "主键非空");
//        }
//        return authDataAccess.openByIds(ids);
//    }
//
//
//    // 查询LIST
//    @RequestMapping(value = "/auth/selectAuth", method = RequestMethod.POST)
//    List<AuthDto> selectAuth(AuthDto authDto) {
//        if (authDto == null) {
//            authDto = new AuthDto();
//        }
//        return authDataAccess.selectAuth(authDto);
//    }
//
//    @RequestMapping(value = "/auth/updateAuth", method = RequestMethod.POST)
//    ResultResponse updateAuth(AuthDto authDto) {
//        if (authDto == null || authDto.getPkAuthId() == null || authDto.getIsActive() == null) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "逻辑删除及主键非空");
//        }
//        return authDataAccess.updateAuth(authDto);
//    }
//}
