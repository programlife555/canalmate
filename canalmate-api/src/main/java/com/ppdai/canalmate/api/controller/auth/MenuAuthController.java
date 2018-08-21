package com.ppdai.canalmate.api.controller.auth;
//package com.ppdai.console.api.controller.auth;
//
//import com.ppdai.console.api.dataaccess.auth.MenuAuthDataAccess;
//import com.ppdai.console.api.entity.dto.MenuAuthDto;
//import com.ppdai.console.api.entity.dto.ResultResponse;
//import com.ppdai.console.api.entity.dto.UserReponseEnum;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
///*
// * 
// */
//@RestController
//@EnableAutoConfiguration
//public class MenuAuthController {
//
//    @Autowired
//    MenuAuthDataAccess menuAuthDataAccess;
//
//    // 新增(给权限新增菜单)
//    @RequestMapping(value = "/menuAuth/insert", method = RequestMethod.POST)
//    ResultResponse insertMenuAuth(MenuAuthDto menuAuthDto) {
//        if (menuAuthDto == null || StringUtils.isBlank(menuAuthDto.getAuthCode()) || StringUtils.isBlank(menuAuthDto.getMenuCode())) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "权限编号、菜单编号非空");
//        }
//        return menuAuthDataAccess.insertMenuAnth(menuAuthDto);
//    }
//
//    // 删除(给权限删除菜单)
//    @RequestMapping(value = "/menuAuth/delete", method = RequestMethod.POST)
//    ResultResponse deleteMenuAuth(MenuAuthDto menuAuthDto) {
//        if (menuAuthDto == null || menuAuthDto.getPkMenuAuthCode() == null) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "主键非空");
//        }
//        return menuAuthDataAccess.deleteMenuAuth(menuAuthDto);
//    }
//
//
//    @RequestMapping(value = "/menuAuth/update", method = RequestMethod.POST)
//    ResultResponse updateMenuAuth(MenuAuthDto menuAuthDto) {
//        if (menuAuthDto == null || menuAuthDto.getPkMenuAuthCode() == null || menuAuthDto.getIsActive() == null) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "逻辑删除及主键非空");
//        }
//        return menuAuthDataAccess.updateMenuAuth(menuAuthDto);
//    }
//
//    // 条件查询LIST(菜单、权限)
//    @RequestMapping(value = "/menuAuth/selectMenuAuth", method = RequestMethod.POST)
//    List<MenuAuthDto> selectMenuAuth(MenuAuthDto menuAuthDto) {
//        if (menuAuthDto == null) {
//            menuAuthDto = new MenuAuthDto();
//        }
//        return menuAuthDataAccess.selectMenuAuth(menuAuthDto);
//    }
//
//
//}
//
