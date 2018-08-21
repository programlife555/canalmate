package com.ppdai.canalmate.api.dataaccess.auth;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.ppdai.canalmate.api.entity.dto.ResultResponse;
import com.ppdai.canalmate.api.entity.dto.RoleDto;
import com.ppdai.canalmate.api.entity.dto.RoleReponseEnum;
import com.ppdai.canalmate.api.entity.dto.UserDto;
import com.ppdai.canalmate.api.entity.dto.UserRoleDto;
import com.ppdai.canalmate.common.utils.DateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongyun on 2017/11/21.
 */
@Component
public class UserRoleDataAccess {
    @Autowired
    @Lazy
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlJdbcTemplate;

    @Autowired
    private UserDataAccess userDataAccess;
    @Autowired
    private RoleDataAccess roleDataAccess;


    // 角色是否已存在
    private static String selecUserRoleByCodeSql = "select count(*) from  tb_user_role where user_code = ? and role_code = ?  and isactive = 1";

    //新增角色
    private static String createUserRoleSql = "insert into tb_user_role(user_code,user_name,role_code,role_name,create_user) values (?,?,?,?,?)";

    // 通过用户code删除角色
    private static String deleteUserRoleSqlByUserCode = "delete from tb_user_role where user_code = ?";

    // 删除角色
    private static String deleteUserRoleSql = "update tb_user_role set update_user = ?, isactive = 0  where pk_user_role_code = ?";

    // 修改
    private static String updateUserRoleSql = "update tb_user_role set update_user = ?, isactive = ?  where pk_user_role_code = ?";

    //物理删除用户的角色
    public ResultResponse deleteUserRoleByUserCode(UserRoleDto userRoleDto) {
        int i = mysqlJdbcTemplate.update(deleteUserRoleSqlByUserCode, new Object[]{userRoleDto.getUserCode()});

        return i == 1 ? new ResultResponse(true, RoleReponseEnum.SUCCEED.getResCode(), RoleReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), RoleReponseEnum.FAIL.getResMsg());
    }
    
    public ResultResponse deleteUserRole(UserRoleDto userRoleDto) {
        int i = mysqlJdbcTemplate.update(deleteUserRoleSql, new Object[]{userRoleDto.getUpdateUser(), userRoleDto.getPkUserRoleId()});

        return i == 1 ? new ResultResponse(true, RoleReponseEnum.SUCCEED.getResCode(), RoleReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), RoleReponseEnum.FAIL.getResMsg());
    }

    public ResultResponse updateUserRole(UserRoleDto userRoleDto) {
        int i = mysqlJdbcTemplate.update(updateUserRoleSql, new Object[]{userRoleDto.getUpdateUser(), userRoleDto.getIsActive(), userRoleDto.getPkUserRoleId()});

        return i == 1 ? new ResultResponse(true, RoleReponseEnum.SUCCEED.getResCode(), RoleReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), RoleReponseEnum.FAIL.getResMsg());
    }

    /**
     * 用户角色列表
     *
     * @return
     */
    public List<UserRoleDto> selectRole(UserRoleDto userRoleDto) {
        List<UserRoleDto> userRoleDtoList = new ArrayList<UserRoleDto>();
        StringBuffer selectRoleSql = new StringBuffer("select pk_user_role_code, user_code, user_name, role_code, role_name, create_user, update_user, inserttime, updatetime, isactive from tb_user_role where 1 = 1 ");
        if (userRoleDto.getPkUserRoleId() != null && userRoleDto.getPkUserRoleId() > 0) {
            selectRoleSql.append(" and pk_user_role_code = ").append(userRoleDto.getPkUserRoleId());
        }
        if (StringUtils.isNotBlank(userRoleDto.getUserCode())) {
            selectRoleSql.append(" and user_code like '%").append(userRoleDto.getUserCode()).append("%' ");
        }
        if (StringUtils.isNotBlank(userRoleDto.getUserName())) {
            selectRoleSql.append(" and user_name like '%").append(userRoleDto.getUserName()).append("%' ");
        }
        if (StringUtils.isNotBlank(userRoleDto.getRoleCode())) {
            selectRoleSql.append(" and role_code ='").append(userRoleDto.getRoleCode()).append("' ");
        }
        if (StringUtils.isNotBlank(userRoleDto.getRoleName())) {
            selectRoleSql.append(" and role_name like '%").append(userRoleDto.getRoleName()).append("%' ");
        }
        if (userRoleDto.getIsActive() != null) {
            selectRoleSql.append(" and isactive = ").append(userRoleDto.getIsActive());
        }
        mysqlJdbcTemplate.query(selectRoleSql.toString(), new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                UserRoleDto userRoleDto1 = new UserRoleDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), DateUtil.formatDate(rs.getString(8)), DateUtil.formatDate(rs.getString(9)), rs.getByte(10));
                userRoleDtoList.add(userRoleDto1);
            }
        });
        return userRoleDtoList;
    }

    /**
     * 新增用户角色
     *
     * @param userRoleDto
     * @return
     */
    public ResultResponse insertUserRole(UserRoleDto userRoleDto) {
        Integer count = mysqlJdbcTemplate.queryForObject(selecUserRoleByCodeSql, Integer.class,
                new Object[]{userRoleDto.getUserCode(), userRoleDto.getRoleCode()});
        if (count != 0) {
            return new ResultResponse(false, RoleReponseEnum.EXIST.getResCode(), RoleReponseEnum.EXIST.getResMsg());
        }
        UserDto userDto = new UserDto();
        userDto.setUserCode(userRoleDto.getUserCode());
        userDto.setIsActive(Byte.valueOf("1"));
        List<UserDto> userDtoList = userDataAccess.selectUserByParam(userDto);

        RoleDto roleDto = new RoleDto();
        roleDto.setRoleCode(userRoleDto.getRoleCode());
        roleDto.setIsActive(Byte.valueOf("1"));
        List<RoleDto> roleDtoList = roleDataAccess.selectRole(roleDto);

        if (userDtoList == null || userDtoList.size() == 0) {
            return new ResultResponse(false, RoleReponseEnum.USER_NOT_EXIST.getResCode(), RoleReponseEnum.USER_NOT_EXIST.getResMsg());
        }

        if (roleDtoList == null || roleDtoList.size() == 0) {
            return new ResultResponse(false, RoleReponseEnum.ROLE_NOT_EXIST.getResCode(), RoleReponseEnum.ROLE_NOT_EXIST.getResMsg());
        }

        int i = mysqlJdbcTemplate.update(createUserRoleSql, new Object[]{userRoleDto.getUserCode(), userDtoList.get(0).getUserName(), userRoleDto.getRoleCode(), roleDtoList.get(0).getRoleName(), userRoleDto.getCreateUser()});

        return i == 1 ? new ResultResponse(true, RoleReponseEnum.SUCCEED.getResCode(), RoleReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), RoleReponseEnum.FAIL.getResMsg());

    }


    public ResultResponse deleteByIds(String[] ids, String userCode) {

        String deleteUserRoleByIdsSql = "update tb_user_role set isactive = 0,update_user = ? where pk_user_role_code in (";
        for (int i = 0; i < ids.length - 1; i++) {
            deleteUserRoleByIdsSql = deleteUserRoleByIdsSql + ids[i] + " ,";
        }
        deleteUserRoleByIdsSql = deleteUserRoleByIdsSql + ids[ids.length - 1] + " )";

        int i = mysqlJdbcTemplate.update(deleteUserRoleByIdsSql, new Object[]{userCode});

        return i >= 1 ? new ResultResponse(true, RoleReponseEnum.SUCCEED.getResCode(), RoleReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), RoleReponseEnum.FAIL.getResMsg());
    }


    public ResultResponse openByIds(String[] ids,String userCode) {

        String openUserRoleByIdsSql = "update tb_user_role set isactive = 1,update_user=? where pk_user_role_code in (";
        for (int i = 0; i < ids.length - 1; i++) {
            openUserRoleByIdsSql = openUserRoleByIdsSql + ids[i] + " ,";
        }
        openUserRoleByIdsSql = openUserRoleByIdsSql + ids[ids.length - 1] + " )";

        int i = mysqlJdbcTemplate.update(openUserRoleByIdsSql,new Object[]{userCode});

        return i >= 1 ? new ResultResponse(true, RoleReponseEnum.SUCCEED.getResCode(), RoleReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), RoleReponseEnum.FAIL.getResMsg());
    }
}

