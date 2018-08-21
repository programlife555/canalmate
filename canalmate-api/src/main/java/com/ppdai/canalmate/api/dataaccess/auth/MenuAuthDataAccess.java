package com.ppdai.canalmate.api.dataaccess.auth;
//package com.ppdai.console.api.dataaccess.auth;
//
//import com.ppdai.console.api.entity.dto.*;
//import com.ppdai.console.common.utilities.DateUtil;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowCallbackHandler;
//import org.springframework.stereotype.Component;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 菜单权限Data
// *
// * @author yanxd
// */
//@Component
//public class MenuAuthDataAccess {
//    @Autowired
//    @Lazy
//    @Qualifier("mysqlJdbcTemplate")
//    private JdbcTemplate mysqlJdbcTemplate;
//
//    @Autowired
//    private MenuDataAccess menuDataAccess;
//
//    @Autowired
//    private AuthDataAccess authDataAccess;
//
//    // 菜单权限是否已存在
//    private static String selectMenuAuthByCodeSql = "SELECT COUNT(*) FROM  TB_MENU_AUTH WHERE MENU_CODE = ? AND AUTH_CODE = ? AND ISACTIVE = 1";
//
//    // 新增菜单权限
//    private static String createMenuAuthSql = "INSERT INTO TB_MENU_AUTH(MENU_CODE, MENU_NAME, AUTH_CODE, AUTH_NAME) VALUES (?,?,?,?)";
//
//    // 删除菜单权限
//    private static String deleteMenuAuthSql = "UPDATE TB_MENU_AUTH SET ISACTIVE = 0 WHERE PK_MENU_AUTH_CODE = ?";
//
//    private static String updateMenuAuthSql = "UPDATE TB_MENU_AUTH SET ISACTIVE = ? WHERE PK_MENU_AUTH_CODE = ?";
//
//    /**
//     * 删除菜单权限
//     *
//     * @return
//     */
//    public ResultResponse deleteMenuAuth(MenuAuthDto menuAuthDto) {
//        int i = mysqlJdbcTemplate.update(deleteMenuAuthSql, new Object[]{menuAuthDto.getPkMenuAuthCode()});
//
//        return i == 0 ? new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), UserReponseEnum.FAIL.getResMsg())
//                : new ResultResponse(true, UserReponseEnum.SUCCEED.getResCode(), UserReponseEnum.SUCCEED.getResMsg());
//    }
//
//    /**
//     * 修改菜单权限
//     *
//     * @return
//     */
//    public ResultResponse updateMenuAuth(MenuAuthDto menuAuthDto) {
//        int i = mysqlJdbcTemplate.update(updateMenuAuthSql, new Object[]{menuAuthDto.getIsActive(), menuAuthDto.getPkMenuAuthCode()});
//
//        return i == 0 ? new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), UserReponseEnum.FAIL.getResMsg())
//                : new ResultResponse(true, UserReponseEnum.SUCCEED.getResCode(), UserReponseEnum.SUCCEED.getResMsg());
//    }
//
//    /**
//     * 菜单权限列表
//     *
//     * @return
//     */
//    public List<MenuAuthDto> selectMenuAuth(MenuAuthDto menuAuthDto) {
//        List<MenuAuthDto> menuAuthList = new ArrayList<MenuAuthDto>();
//        StringBuffer selectMenuAuthSql = new StringBuffer("SELECT PK_MENU_AUTH_CODE, MENU_CODE, MENU_NAME, AUTH_CODE, AUTH_NAME, CREATE_USER, UPDATE_USER, INSERTTIME, UPDATETIME, ISACTIVE FROM TB_MENU_AUTH WHERE 1 = 1 ");
//        if (menuAuthDto.getPkMenuAuthCode() != null && menuAuthDto.getPkMenuAuthCode() > 0) {
//            selectMenuAuthSql.append(" AND PK_MENU_AUTH_CODE = ").append(menuAuthDto.getPkMenuAuthCode());
//        }
//        if (StringUtils.isNotBlank(menuAuthDto.getMenuCode())) {
//            selectMenuAuthSql.append(" AND MENU_CODE LIKE '%").append(menuAuthDto.getMenuCode()).append("%' ");
//        }
//        if (StringUtils.isNotBlank(menuAuthDto.getMenuName())) {
//            selectMenuAuthSql.append(" AND MENU_NAME LIKE '%").append(menuAuthDto.getMenuName()).append("%' ");
//        }
//        if (StringUtils.isNotBlank(menuAuthDto.getAuthCode())) {
//            selectMenuAuthSql.append(" AND AUTH_CODE LIKE '%").append(menuAuthDto.getAuthCode()).append("%' ");
//        }
//        if (StringUtils.isNotBlank(menuAuthDto.getAuthName())) {
//            selectMenuAuthSql.append(" AND AUTH_NAME LIKE '%").append(menuAuthDto.getAuthName()).append("%' ");
//        }
//        if (menuAuthDto.getIsActive() != null) {
//            selectMenuAuthSql.append(" AND ISACTIVE = ").append(menuAuthDto.getIsActive());
//        }
//
//        mysqlJdbcTemplate.query(selectMenuAuthSql.toString(), new RowCallbackHandler() {
//            @Override
//            public void processRow(ResultSet rs) throws SQLException {
//                MenuAuthDto MenuAuthDto = new MenuAuthDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)
//                        , rs.getString(6), rs.getString(7), DateUtil.formatDate(rs.getString(8)), DateUtil.formatDate(rs.getString(9)), rs.getByte(10));
//                menuAuthList.add(MenuAuthDto);
//            }
//        });
//        return menuAuthList;
//    }
//
//    /**
//     * 新增菜单权限
//     *
//     * @param menuAuthDto
//     * @return
//     */
//    public ResultResponse insertMenuAnth(MenuAuthDto menuAuthDto) {
//        // 判断是否已存在
//        Integer count = mysqlJdbcTemplate.queryForObject(selectMenuAuthByCodeSql, Integer.class,
//                new Object[]{menuAuthDto.getMenuCode(), menuAuthDto.getAuthCode()});
//        if (count != 0) {
//            return new ResultResponse(false, UserReponseEnum.MENU_AUTH_EXIST.getResCode(),
//                    UserReponseEnum.MENU_AUTH_EXIST.getResMsg());
//        }
//
//        MenuDto menuDto = new MenuDto();
//        menuDto.setMenuCode(menuAuthDto.getMenuCode());
//        menuDto.setIsActive(Byte.valueOf("1"));
//        List<MenuDto> menuDtoList = menuDataAccess.selectMenu(menuDto);
//        if (menuDtoList == null || menuDtoList.size() == 0) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "此菜单不存在");
//        }
//        AuthDto authDto = new AuthDto();
//        authDto.setAuthCode(menuAuthDto.getAuthCode());
//        authDto.setIsActive(Byte.valueOf("1"));
//        List<AuthDto> authDtoList = authDataAccess.selectAuth(authDto);
//        if (authDtoList == null || authDtoList.size() == 0) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "此权限不存在");
//        }
//        int i = mysqlJdbcTemplate.update(createMenuAuthSql, new Object[]{menuAuthDto.getMenuCode(), menuDtoList.get(0).getMenuName(), menuAuthDto.getAuthCode(), authDtoList.get(0).getAuthName()});
//
//        return i == 1 ? new ResultResponse(true, UserReponseEnum.SUCCEED.getResCode(), UserReponseEnum.SUCCEED.getResMsg())
//                : new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), UserReponseEnum.FAIL.getResMsg());
//    }
//}
