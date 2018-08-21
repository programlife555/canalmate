package com.ppdai.canalmate.api.dataaccess.auth;
//package com.ppdai.console.api.dataaccess.auth;
//
//import com.ppdai.console.api.entity.dto.AuthDto;
//import com.ppdai.console.api.entity.dto.ResultResponse;
//import com.ppdai.console.api.entity.dto.UserReponseEnum;
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
// * 权限Data
// *
// * @author yanxd
// */
//@Component
//public class AuthDataAccess {
//    @Autowired
//    @Lazy
//    @Qualifier("mysqlJdbcTemplate")
//    private JdbcTemplate mysqlJdbcTemplate;
//
//    // 是否存在
//    private static String selectAuthByCodeSql = "SELECT COUNT(*) FROM  TB_AUTH WHERE AUTH_CODE = ?  AND ISACTIVE = 1";
//
//    // 新增
//    private static String createAuthSql = "INSERT INTO TB_AUTH(AUTH_CODE,AUTH_NAME) VALUES (?,?)";
//
//    // 删除
//    private static String deleteAuthSql = "UPDATE TB_AUTH SET ISACTIVE = 0 WHERE PK_AUTH_ID = ?";
//
//    //修改
//    private static String updateAuthSql = "UPDATE TB_AUTH SET ISACTIVE = ? WHERE PK_AUTH_ID = ?";
//
//    public ResultResponse insertAuth(AuthDto authDto) {
//        // 判断是否已存在
//        Integer count = mysqlJdbcTemplate.queryForObject(selectAuthByCodeSql, Integer.class, authDto.getAuthCode());
//        if (count != 0) {
//            return new ResultResponse(false, UserReponseEnum.AUTH_EXIST.getResCode(), UserReponseEnum.AUTH_EXIST.getResMsg());
//        }
//
//        // 新增
//        int i = mysqlJdbcTemplate.update(createAuthSql, new Object[]{authDto.getAuthCode(), authDto.getAuthName()});
//
//        return i == 1 ? new ResultResponse(true, UserReponseEnum.SUCCEED.getResCode(), UserReponseEnum.SUCCEED.getResMsg())
//                : new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), UserReponseEnum.FAIL.getResMsg());
//    }
//
//    public ResultResponse deleteAuth(AuthDto authDto) {
//        int i = mysqlJdbcTemplate.update(deleteAuthSql, new Object[]{authDto.getPkAuthId()});
//        return i == 1 ? new ResultResponse(true, UserReponseEnum.SUCCEED.getResCode(), UserReponseEnum.SUCCEED.getResMsg())
//                : new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), UserReponseEnum.FAIL.getResMsg());
//    }
//
//    public List<AuthDto> selectAuth(AuthDto authDto) {
//        List<AuthDto> authList = new ArrayList<AuthDto>();
//        StringBuffer selectAuthSql = new StringBuffer("SELECT PK_AUTH_ID, AUTH_CODE, AUTH_NAME, CREATE_USER, UPDATE_USER, INSERTTIME, UPDATETIME, ISACTIVE FROM TB_AUTH WHERE 1 = 1 ");
//        if (authDto.getPkAuthId() != null && authDto.getPkAuthId() > 0) {
//            selectAuthSql.append(" AND PK_AUTH_ID = ").append(authDto.getPkAuthId());
//        }
//        if (StringUtils.isNotBlank(authDto.getAuthCode())) {
//            selectAuthSql.append(" AND AUTH_CODE = '").append(authDto.getAuthCode()).append("' ");
//        }
//        if (StringUtils.isNotBlank(authDto.getAuthName())) {
//            selectAuthSql.append(" AND AUTH_NAME LIKE '%").append(authDto.getAuthName()).append("%' ");
//        }
//        if (authDto.getIsActive() != null) {
//            selectAuthSql.append(" AND ISACTIVE = ").append(authDto.getIsActive());
//        }
//        mysqlJdbcTemplate.query(selectAuthSql.toString(), new RowCallbackHandler() {
//            @Override
//            public void processRow(ResultSet rs) throws SQLException {
//                AuthDto authDto = new AuthDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
//                        rs.getString(5), DateUtil.formatDate(rs.getString(6)), DateUtil.formatDate(rs.getString(6)), rs.getByte(8));
//                authList.add(authDto);
//            }
//        });
//        return authList;
//    }
//
//    /**
//     *
//     *
//     * @param authDto
//     * @return
//     */
//    public ResultResponse updateAuth(AuthDto authDto) {
//        int i = mysqlJdbcTemplate.update(updateAuthSql, new Object[]{authDto.getIsActive(), authDto.getPkAuthId()});
//
//        return i == 1 ? new ResultResponse(true, UserReponseEnum.SUCCEED.getResCode(), UserReponseEnum.SUCCEED.getResMsg())
//                : new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), UserReponseEnum.FAIL.getResMsg());
//    }
//
//    public ResultResponse deleteByIds(String[] ids) {
//
//        String deleteUserByIdsSql = "UPDATE TB_AUTH SET ISACTIVE = 0 WHERE PK_AUTH_ID  IN (";
//        for (int i = 0; i < ids.length - 1; i++) {
//            deleteUserByIdsSql = deleteUserByIdsSql + ids[i] + " ,";
//        }
//        deleteUserByIdsSql = deleteUserByIdsSql + ids[ids.length - 1] + " )";
//
//        int i = mysqlJdbcTemplate.update(deleteUserByIdsSql);
//
//        return i >= 1 ? new ResultResponse(true, UserReponseEnum.SUCCEED.getResCode(), UserReponseEnum.SUCCEED.getResMsg())
//                : new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), UserReponseEnum.FAIL.getResMsg());
//    }
//
//    public ResultResponse openByIds(String[] ids) {
//        String deleteUserByIdsSql = "UPDATE TB_AUTH SET ISACTIVE = 1 WHERE PK_AUTH_ID  IN (";
//        for (int i = 0; i < ids.length - 1; i++) {
//            deleteUserByIdsSql = deleteUserByIdsSql + ids[i] + " ,";
//        }
//        deleteUserByIdsSql = deleteUserByIdsSql + ids[ids.length - 1] + " )";
//
//        int i = mysqlJdbcTemplate.update(deleteUserByIdsSql);
//
//        return i >= 1 ? new ResultResponse(true, UserReponseEnum.SUCCEED.getResCode(), UserReponseEnum.SUCCEED.getResMsg())
//                : new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), UserReponseEnum.FAIL.getResMsg());
//    }
//}
