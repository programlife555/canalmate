package com.ppdai.canalmate.api.dataaccess.auth;
//package com.ppdai.console.api.dataaccess.auth;
//
//import com.ppdai.console.api.entity.dto.*;
////import com.ppdai.console.api.entity.dto.strategy.AlarmDingDingDto;
////import com.ppdai.console.api.entity.dto.strategy.StrategySettingResEnum;
//import com.ppdai.console.common.utilities.DateUtil;
////import com.ppdai.data.goldeye.dashboard.domain.dataaccess.strategymanage.AlarmDingDingDataAccess;
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
// * 用户权限Data
// *
// * @author yanxd
// */
//@Component
//public class UserAuthDataAccess {
//    @Autowired
//    @Lazy
//    @Qualifier("mysqlJdbcTemplate")
//    private JdbcTemplate mysqlJdbcTemplate;
//
//    @Autowired
//    private UserDataAccess userDataAccess;
//
//    @Autowired
////    private AuthDataAccess authDataAccess;
//
////    @Autowired
////    private AlarmDingDingDataAccess alarmDingDingDataAccess;
//    // 用户权限是否已存在
//    private static String selectUserAuthByCodeSql = "SELECT COUNT(*) FROM  TB_USER_AUTH WHERE AUTH_CODE = ? AND USER_CODE = ?  AND ISACTIVE = 1";
//
//    // 新增用户权限
//    private static String createUserAuthSql = "INSERT INTO TB_USER_AUTH(USER_CODE,USER_NAME,AUTH_CODE,AUTH_NAME) VALUES (?,?,?,?)";
//
//    // 删除用户权限
//    private static String deleteUserAuthSql = "UPDATE TB_USER_AUTH SET ISACTIVE = 0 WHERE PK_USER_AUTH_CODE = ?";
//
//    // 修改用户权限
//    private static String updateUserAuthSql = "UPDATE TB_USER_AUTH SET ISACTIVE = ? WHERE PK_USER_AUTH_CODE = ?";
//
//    public ResultResponse deleteUserAuth(UserAuthDto userAuthDto) {
//        int i = mysqlJdbcTemplate.update(deleteUserAuthSql, userAuthDto.getPkUserAuthCode());
//
//        return i == 1 ? new ResultResponse(true, UserReponseEnum.SUCCEED.getResCode(), UserReponseEnum.SUCCEED.getResMsg())
//                : new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), UserReponseEnum.FAIL.getResMsg());
//    }
//
//    public ResultResponse updateUserAuth(UserAuthDto userAuthDto) {
//        int i = mysqlJdbcTemplate.update(updateUserAuthSql, userAuthDto.getIsActive(), userAuthDto.getPkUserAuthCode());
//
//        return i == 1 ? new ResultResponse(true, UserReponseEnum.SUCCEED.getResCode(), UserReponseEnum.SUCCEED.getResMsg())
//                : new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), UserReponseEnum.FAIL.getResMsg());
//    }
//
//    /**
//     * 用户权限列表
//     *
//     * @return
//     */
//    public List<UserAuthDto> selectUserAuth(UserAuthDto userAuthDto) {
//        List<UserAuthDto> userAuthList = new ArrayList<UserAuthDto>();
//        StringBuffer selectUserAuthSql = new StringBuffer("SELECT PK_USER_AUTH_CODE, USER_CODE, USER_NAME, AUTH_CODE, AUTH_NAME, CREATE_USER, UPDATE_USER, INSERTTIME, UPDATETIME, ISACTIVE FROM TB_USER_AUTH WHERE 1 = 1 ");
//        if (userAuthDto.getPkUserAuthCode() != null && userAuthDto.getPkUserAuthCode() > 0) {
//            selectUserAuthSql.append(" AND PK_USER_AUTH_CODE = ").append(userAuthDto.getPkUserAuthCode());
//        }
//        if (StringUtils.isNotBlank(userAuthDto.getUserCode())) {
//            selectUserAuthSql.append(" AND USER_CODE LIKE '%").append(userAuthDto.getUserCode()).append("%' ");
//        }
//        if (StringUtils.isNotBlank(userAuthDto.getUserName())) {
//            selectUserAuthSql.append(" AND USER_NAME LIKE '%").append(userAuthDto.getUserName()).append("%' ");
//        }
//        if (StringUtils.isNotBlank(userAuthDto.getAuthCode())) {
//            selectUserAuthSql.append(" AND AUTH_CODE LIKE '%").append(userAuthDto.getAuthCode()).append("%' ");
//        }
//        if (StringUtils.isNotBlank(userAuthDto.getAuthName())) {
//            selectUserAuthSql.append(" AND AUTH_NAME LIKE '%").append(userAuthDto.getAuthName()).append("%' ");
//        }
//        if (userAuthDto.getIsActive() != null) {
//            selectUserAuthSql.append(" AND ISACTIVE = ").append(userAuthDto.getIsActive());
//        }
//        mysqlJdbcTemplate.query(selectUserAuthSql.toString(), new RowCallbackHandler() {
//            @Override
//            public void processRow(ResultSet rs) throws SQLException {
//                UserAuthDto userAuthDto = new UserAuthDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
//                        rs.getString(6), rs.getString(7), DateUtil.formatDate(rs.getString(8)),DateUtil.formatDate(rs.getString(9)), rs.getByte(10));
//                userAuthList.add(userAuthDto);
//            }
//        });
//        return userAuthList;
//    }
//
//    /**
//     * 新增用户权限
//     *
//     * @param userAuthDto
//     * @return
//     */
//    public ResultResponse insertUserAuth(UserAuthDto userAuthDto) {
//        Integer count = mysqlJdbcTemplate.queryForObject(selectUserAuthByCodeSql, Integer.class,
//                new Object[]{userAuthDto.getAuthCode(), userAuthDto.getUserCode()});
//        if (count != 0) {
//            return new ResultResponse(false, UserReponseEnum.USER_AUTH_EXIST.getResCode(), UserReponseEnum.USER_AUTH_EXIST.getResMsg());
//        }
//        UserDto userDto = new UserDto();
//        userDto.setUserCode(userAuthDto.getUserCode());
//        userDto.setIsActive(Byte.valueOf("1"));
//        List<UserDto> userDtoList = userDataAccess.selectUserByParam(userDto);
//        AuthDto authDto = new AuthDto();
//        authDto.setAuthCode(userAuthDto.getAuthCode());
//        authDto.setIsActive(Byte.valueOf("1"));
//        int i=0;
//        List<AuthDto> authDtoList = authDataAccess.selectAuth(authDto);
//        if (authDtoList == null || authDtoList.size() == 0) {
//            return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "此权限不存在");
//        }
////        AlarmDingDingDto alarmDingDingDto=new AlarmDingDingDto();
////        alarmDingDingDto.setDingCode(userAuthDto.getUserCode());//dingCode相当于userCode、
////        alarmDingDingDto.setIsActive(Byte.valueOf("1"));
////        List<AlarmDingDingDto> alarmDingDingDtoList=alarmDingDingDataAccess.selectDingUserByParam(alarmDingDingDto);
////        if (alarmDingDingDtoList.size()>0 && userDtoList.size()>0){
////            return new ResultResponse(false, UserReponseEnum.USER_EXIST.getResCode(), "用户编号冲突，请联系管理员");
////        }else {
////            if ((userDtoList == null || userDtoList.size() == 0) && (alarmDingDingDtoList == null || alarmDingDingDtoList.size() == 0)) {
////                return new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), "此用户与钉钉都不存在");
////            } else if (userDtoList != null && userDtoList.size() != 0) {
////                i = mysqlJdbcTemplate.update(createUserAuthSql, new Object[]{userAuthDto.getUserCode(), userDtoList.get(0).getUserName(), userAuthDto.getAuthCode(), authDtoList.get(0).getAuthName()});
////            } else if (alarmDingDingDtoList != null && alarmDingDingDtoList.size() != 0) {
////                i = mysqlJdbcTemplate.update(createUserAuthSql, new Object[]{userAuthDto.getUserCode(), alarmDingDingDtoList.get(0).getDingName(), userAuthDto.getAuthCode(), authDtoList.get(0).getAuthName()});
////            } else {
////            }
////        }
//        return i == 1 ? new ResultResponse(true, UserReponseEnum.SUCCEED.getResCode(), UserReponseEnum.SUCCEED.getResMsg())
//                : new ResultResponse(false, UserReponseEnum.FAIL.getResCode(), UserReponseEnum.FAIL.getResMsg());
//    }
//
//    public ResultResponse deleteByIds(String[] ids) {
//
//        String deleteUserByIdsSql = "UPDATE TB_USER_AUTH SET ISACTIVE = 0 WHERE PK_USER_AUTH_CODE IN (";
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
//
//        String deleteUserByIdsSql = "UPDATE TB_USER_AUTH SET ISACTIVE = 1 WHERE PK_USER_AUTH_CODE IN (";
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
//
