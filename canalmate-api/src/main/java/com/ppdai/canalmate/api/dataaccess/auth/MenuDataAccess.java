package com.ppdai.canalmate.api.dataaccess.auth;

import com.ppdai.canalmate.api.entity.dto.MenuDto;
import com.ppdai.canalmate.api.entity.dto.ResultResponse;
import com.ppdai.canalmate.common.utils.DateUtil;
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
import java.util.List;

/**
 * 菜单Data
 *
 * @author yanxd
 */
@Component
public class MenuDataAccess {
    @Autowired
    @Lazy
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlJdbcTemplate;

    // 菜单是否已存在
    private static String selectMenuByCodeSql = "SELECT COUNT(*) FROM  TB_MENU WHERE MENU_CODE = ?  AND ISACTIVE = 1";

    // 新增菜单
    private static String createMenuSql = "INSERT INTO TB_MENU(MENU_CODE,MENU_NAME,SUPERIOR_MENU,MENU_URL,MENU_SERIAL) VALUES (?,?,?,?,?)";

    // 删除菜单
    private static String deleteMenuSql = "UPDATE TB_MENU  SET ISACTIVE = 0 WHERE PK_MENU_ID = ?";

    private static String updateMenuSql = "UPDATE TB_MENU  SET SUPERIOR_MENU = ?, MENU_URL = ?, MENU_SERIAL = ?, ISACTIVE = ? WHERE PK_MENU_ID = ?";

    /**
     * 新增菜单
     *
     * @param
     * @return
     */
    public ResultResponse insertMenu(MenuDto menuDto) {
        // 判断是否已存在
        int count = mysqlJdbcTemplate.queryForObject(selectMenuByCodeSql, Integer.class, menuDto.getMenuCode());
        if (count != 0) {
            return new ResultResponse(false, ReponseEnum.MENU_EXIST.getResCode(), ReponseEnum.MENU_EXIST.getResMsg());
        }
        // 新增
        int i = mysqlJdbcTemplate.update(createMenuSql, new Object[]{menuDto.getMenuCode(), menuDto.getMenuName(),
                menuDto.getSuperiorMenu(), menuDto.getMenuUrl(), menuDto.getMenuSerial()});
        return i == 1 ? new ResultResponse(true, ReponseEnum.SUCCEED.getResCode(), ReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, ReponseEnum.FAIL.getResCode(), ReponseEnum.FAIL.getResMsg());
    }

    /**
     * 删除菜单
     *
     * @param
     * @return
     */
    public ResultResponse deleteMenu(MenuDto menuDto) {
        int i = mysqlJdbcTemplate.update(deleteMenuSql, new Object[]{menuDto.getPkMenuId()});
        return i == 0 ? new ResultResponse(false, ReponseEnum.FAIL.getResCode(), ReponseEnum.FAIL.getResMsg())
                : new ResultResponse(true, ReponseEnum.SUCCEED.getResCode(), ReponseEnum.SUCCEED.getResMsg());
    }

    public ResultResponse updateMenu(MenuDto menuDto) {
        int i = mysqlJdbcTemplate.update(updateMenuSql, new Object[]{menuDto.getSuperiorMenu(), menuDto.getMenuUrl(), menuDto.getMenuSerial(), menuDto.getIsActive(), menuDto.getPkMenuId()});
        return i == 0 ? new ResultResponse(false, ReponseEnum.FAIL.getResCode(), ReponseEnum.FAIL.getResMsg())
                : new ResultResponse(true, ReponseEnum.SUCCEED.getResCode(), ReponseEnum.SUCCEED.getResMsg());
    }

    /**
     * 菜单列表
     *
     * @return
     */
    public List<MenuDto> selectMenu(MenuDto menuDto) {
        List<MenuDto> menuList = new ArrayList<MenuDto>();
        StringBuffer selectMenuSql = new StringBuffer("SELECT PK_MENU_ID, MENU_CODE, MENU_NAME, SUPERIOR_MENU, MENU_URL, MENU_SERIAL, CREATE_USER, UPDATE_USER, INSERTTIME, UPDATETIME, ISACTIVE FROM TB_MENU WHERE 1 = 1 ");
        if (menuDto.getPkMenuId() != null && menuDto.getPkMenuId() > 0) {
            selectMenuSql.append(" AND PK_MENU_ID = ").append(menuDto.getPkMenuId());
        }
        if (StringUtils.isNotBlank(menuDto.getMenuCode())) {
            selectMenuSql.append(" AND MENU_CODE = '").append(menuDto.getMenuCode()).append("' ");
        }
        if (StringUtils.isNotBlank(menuDto.getMenuName())) {
            selectMenuSql.append(" AND MENU_NAME LIKE '%").append(menuDto.getMenuName()).append("%' ");
        }
        if (StringUtils.isNotBlank(menuDto.getSuperiorMenu())) {
            selectMenuSql.append(" AND SUPERIOR_MENU LIKE '%").append(menuDto.getSuperiorMenu()).append("%' ");
        }
        if (StringUtils.isNotBlank(menuDto.getMenuUrl())) {
            selectMenuSql.append(" AND MENU_URL LIKE '%").append(menuDto.getMenuUrl()).append("%' ");
        }
        if (menuDto.getMenuSerial() != null) {
            selectMenuSql.append(" AND MENU_SERIAL = ").append(menuDto.getMenuSerial());
        }
        if (menuDto.getIsActive() != null) {
            selectMenuSql.append(" AND ISACTIVE = ").append(menuDto.getIsActive());
        }

        mysqlJdbcTemplate.query(selectMenuSql.toString(), new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                MenuDto menuDto = new MenuDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)
                        , rs.getInt(6), rs.getString(7), rs.getString(8), DateUtil.formatDate(rs.getString(9)), DateUtil.formatDate(rs.getString(10)), rs.getByte(11));
                menuList.add(menuDto);
            }
        });
        return menuList;
    }

}
