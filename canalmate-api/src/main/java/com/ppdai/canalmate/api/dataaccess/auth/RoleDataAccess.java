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
import com.ppdai.canalmate.common.utils.DateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongyun on 2017/11/21.
 */
@Component
public class RoleDataAccess {
    @Autowired
    @Lazy
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlJdbcTemplate;

//    @Autowired
//    private AlarmScriptDataAccess alarmScriptDataAccess;
//
//    @Autowired
//    private AlarmScriptTypedictDataAccess alarmScriptTypedictDataAccess;

    // 角色是否已存在
    private static String selecRoleByCodeSql = "SELECT COUNT(*) FROM  TB_GOLDEYE_ROLE WHERE ROLE_CODE = ? AND ALARM_CODE = ?  AND ISACTIVE = 1";

    //新增角色
    private static String createRoleSql = "INSERT INTO TB_GOLDEYE_ROLE(ROLE_CODE,ROLE_NAME,ALARM_CODE,ALARM_NAME,CREATE_USER) VALUES (?,?,?,?,?)";

    // 删除角色
    private static String deleteRoleSql = "UPDATE TB_GOLDEYE_ROLE SET UPDATE_USER = ? ,ISACTIVE = 0 WHERE PK_ROLE_CODE = ?";

    // 修改用户权限
    private static String updateRoleSql = "UPDATE TB_GOLDEYE_ROLE SET UPDATE_USER = ? , ISACTIVE = ? WHERE PK_ROLE_CODE = ?";


    public ResultResponse deleteRole(RoleDto roleDto) {
        int i = mysqlJdbcTemplate.update(deleteRoleSql, new Object[]{roleDto.getUpdateUser(), roleDto.getPkRoleId()});

        return i == 1 ? new ResultResponse(true, RoleReponseEnum.SUCCEED.getResCode(), RoleReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), RoleReponseEnum.FAIL.getResMsg());
    }

    public ResultResponse updateRole(RoleDto roleDto) {
        int i = mysqlJdbcTemplate.update(updateRoleSql, new Object[]{roleDto.getUpdateUser(), roleDto.getIsActive(), roleDto.getPkRoleId()});

        return i == 1 ? new ResultResponse(true, RoleReponseEnum.SUCCEED.getResCode(), RoleReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), RoleReponseEnum.FAIL.getResMsg());
    }

    /**
     * 用户角色列表
     *
     * @return
     */
    public List<RoleDto> selectRole(RoleDto roleDto) {
        List<RoleDto> roleList = new ArrayList<RoleDto>();
        StringBuffer selectRoleSql = new StringBuffer("select pk_role_code, role_code, role_name, create_user, update_user, inserttime, updatetime, isactive from tb_role where 1 = 1 ");
        if (roleDto.getPkRoleId() != null && roleDto.getPkRoleId() > 0) {
            selectRoleSql.append(" and pk_role_code = ").append(roleDto.getPkRoleId());
        }
        if (StringUtils.isNotBlank(roleDto.getRoleCode())) {
            selectRoleSql.append(" and role_code = '").append(roleDto.getRoleCode()).append("' ");
        }
        if (StringUtils.isNotBlank(roleDto.getRoleName())) {
            selectRoleSql.append(" and role_name like '%").append(roleDto.getRoleName()).append("%' ");
        }
        if (roleDto.getIsActive() != null) {
            selectRoleSql.append(" and isactive = ").append(roleDto.getIsActive());
        }
        mysqlJdbcTemplate.query(selectRoleSql.toString(), new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
            	int pkRoleCode=rs.getInt(1);
            	String roleCode=rs.getString(2);
            	String roleName=rs.getString(3);
            	String createUser=rs.getString(4);
            	String updateUser=rs.getString(5);
            	String inserttime=DateUtil.formatDate(rs.getString(6));
            	String updatetime=DateUtil.formatDate(rs.getString(7));
            	Byte isactive=rs.getByte(8);
                RoleDto roleDto1 = new RoleDto();
                roleDto1.setPkRoleId(pkRoleCode);
                roleDto1.setRoleCode(roleCode);
                roleDto1.setRoleName(roleName);
                roleDto1.setCreateUser(createUser);
                roleDto1.setUpdateUser(updateUser);
                roleDto1.setInsertTime(inserttime);
                roleDto1.setUpdateTime(updatetime);
                roleDto1.setIsActive(isactive);
                
                roleList.add(roleDto1);
            }
        });
        return roleList;
    }

    /**
     * 新增用户角色
     *
     * @param roleDto
     * @return
     */
//    public ResultResponse insertRole(RoleDto roleDto) {
//        Integer count = mysqlJdbcTemplate.queryForObject(selecRoleByCodeSql, Integer.class,
//                new Object[]{roleDto.getRoleCode(), roleDto.getAlarmCode()});
//        if (count != 0) {
//            return new ResultResponse(false, RoleReponseEnum.EXIST.getResCode(), RoleReponseEnum.EXIST.getResMsg());
//        }
//        AlarmScriptDto alarmScriptDto = new AlarmScriptDto();
//        try {
//            alarmScriptDto.setId(Integer.parseInt(roleDto.getAlarmCode()));
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return new ResultResponse(false, RoleReponseEnum.ALARMCODE_NOT_NUMBER.getResCode(), RoleReponseEnum.ALARMCODE_NOT_NUMBER.getResMsg());
//        }
//        alarmScriptDto.setIsActive(Byte.valueOf("1"));
//        List<AlarmScriptDto> alarmScriptDtoList = alarmScriptDataAccess.selectAlarmScript(alarmScriptDto);
//
//        AlarmScriptDictDto alarmScriptDictDto = new AlarmScriptDictDto();
//        alarmScriptDictDto.setKey(roleDto.getRoleCode());
//        alarmScriptDictDto.setType("09");
//        alarmScriptDictDto.setIsActive(Byte.valueOf("1"));
//        List<AlarmScriptDictDto> alarmScriptDictDtoList = alarmScriptTypedictDataAccess.selectAlarmScriptTypedict(alarmScriptDictDto);
//
//        if (alarmScriptDtoList == null || alarmScriptDtoList.size() == 0) {
//            return new ResultResponse(false, RoleReponseEnum.ALARM_NOT_EXIST.getResCode(), RoleReponseEnum.ALARM_NOT_EXIST.getResMsg());
//        }
//
//        if (alarmScriptDictDtoList == null || alarmScriptDictDtoList.size() == 0) {
//            return new ResultResponse(false, RoleReponseEnum.ALARBUSINESS_NOT_EXIST.getResCode(), RoleReponseEnum.ALARBUSINESS_NOT_EXIST.getResMsg());
//        }
//
//        int i = mysqlJdbcTemplate.update(createRoleSql, new Object[]{roleDto.getRoleCode(), alarmScriptDictDtoList.get(0).getValue(), roleDto.getAlarmCode(), alarmScriptDtoList.get(0).getDescri(), roleDto.getCreateUser()});
//
//        return i == 1 ? new ResultResponse(true, RoleReponseEnum.SUCCEED.getResCode(), RoleReponseEnum.SUCCEED.getResMsg())
//                : new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), RoleReponseEnum.FAIL.getResMsg());
//
//    }


    public ResultResponse deleteByIds(String[] ids, String userCode) {

        String deleteRoleByIdsSql = "UPDATE TB_GOLDEYE_ROLE SET ISACTIVE = 0,UPDATE_USER = ? WHERE PK_ROLE_CODE IN (";
        for (int i = 0; i < ids.length - 1; i++) {
            deleteRoleByIdsSql = deleteRoleByIdsSql + ids[i] + " ,";
        }
        deleteRoleByIdsSql = deleteRoleByIdsSql + ids[ids.length - 1] + " )";

        int i = mysqlJdbcTemplate.update(deleteRoleByIdsSql, new Object[]{userCode});

        return i >= 1 ? new ResultResponse(true, RoleReponseEnum.SUCCEED.getResCode(), RoleReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), RoleReponseEnum.FAIL.getResMsg());
    }

    public ResultResponse openByIds(String[] ids,String userCode) {

        String openRoleByIdsSql = "UPDATE TB_GOLDEYE_ROLE SET ISACTIVE = 1,UPDATE_USER = ? WHERE PK_ROLE_CODE IN (";
        for (int i = 0; i < ids.length - 1; i++) {
            openRoleByIdsSql = openRoleByIdsSql + ids[i] + " ,";
        }
        openRoleByIdsSql = openRoleByIdsSql + ids[ids.length - 1] + " )";

        int i = mysqlJdbcTemplate.update(openRoleByIdsSql,new Object[]{userCode});

        return i >= 1 ? new ResultResponse(true, RoleReponseEnum.SUCCEED.getResCode(), RoleReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, RoleReponseEnum.FAIL.getResCode(), RoleReponseEnum.FAIL.getResMsg());
    }
}

