package com.ppdai.canalmate.api.dataaccess.auth;


import com.ppdai.canalmate.api.entity.dto.LoginResponse;
import com.ppdai.canalmate.api.entity.dto.MenuDto;
import com.ppdai.canalmate.api.entity.dto.ResultResponse;
import com.ppdai.canalmate.api.entity.dto.UserDto;
import com.ppdai.canalmate.api.entity.dto.UserRoleDto;
import com.ppdai.canalmate.common.utils.DateUtil;
import com.ppdai.canalmate.common.utils.P;
import com.ppdai.canalmate.common.utils.ReponseEnum;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Data
 *
 * @author yanxd
 */
@Component
public class UserDataAccess {
    @Autowired
    @Lazy
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlJdbcTemplate;
    
    @Autowired
    UserRoleDataAccess userRoleDataAccess;

    //根据rolecode,选择菜单，role_code=1:管理员，role_code=2:普通用户
    private static String selectUserMenuAuthSql = "select t.menu_code,t.menu_name, t.menu_url, t.menu_icon, t.superior_menu, t.menu_serial \r\n" + 
    		"from tb_menu t\r\n" + 
    		"where menu_code in(\r\n" + 
    		"select menu_code\r\n" + 
    		"from tb_menu_role t3\r\n" + 
    		"where role_code = ? \r\n" + 
    		"and t3.isactive = '1'\r\n" + 
    		")\r\n" + 
    		"and t.isactive = '1'";
    		
    		
    // 用户是否已存在
    private static String selectUserByCodeSql = "select count(*) from  tb_user where user_code = ? and isactive = 1";
    private static String selectUserIgnoreActiveByCodeSql = "select count(*) from  tb_user where user_code = ? ";

    // 用户是否已存在(登录)
    private static String selectUserByCodePasswordSql = "select count(*) from  tb_user where user_code = ? and user_password=?";

    // 用户是否是管理员,管理员的role_code是从数据库查的，目前写死
    private static String selectUserIsAdmin = "select count(*) from tb_user_role where user_code=? and role_code=1";

    // 新增用户
    private static String createUserSql = "insert into tb_user(user_code, user_name, user_password, user_mobile, user_email, superior) values (?,?,?,?,?,?)";

    private static String deleteUserSql = "update tb_user set isactive = 0 where pk_user_id = ?";

    /**
     * 根据角色编号查菜单权限
     * role_code=1:管理员，role_code=2:普通用户
     * @param userCode
     * @return
     */
    public List<MenuDto> selectUserMenuAuth(String role_code) {
    	//查询所有菜单
        List<MenuDto> allMenuList  = new ArrayList<MenuDto>();
        mysqlJdbcTemplate.query(selectUserMenuAuthSql, new Object[]{role_code}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                String menuCode = rs.getString(1);
                String menuName = rs.getString(2);
                String menuUrl = rs.getString(3);
                String menuIcon = rs.getString(4);
                String superiorMenu = rs.getString(5);
                int menuSerial = rs.getInt(6);
                MenuDto menu = new MenuDto(menuCode, menuName, superiorMenu, menuUrl,menuIcon, menuSerial);
                allMenuList.add(menu);
            }
        });
        
        
        //遍历检查
//	      for (MenuDto menuItem:allMenuList){
//		      System.out.println(menuItem.getMenuCode()+"==="+menuItem.getMenuName()+"==superMenu:"+menuItem.getSuperiorMenu());
//	      }
	      
	      //要返回的根节点
	      List<MenuDto> rootMenu = new ArrayList<MenuDto>();
	      for (MenuDto nav : allMenuList) {
	    	if("0".equals(nav.getSuperiorMenu())) {//父节点是0的，为根节点。
	          rootMenu.add(nav);
	        }
	      }
	      /* 根据Menu类的order排序 */
	      Collections.sort(rootMenu, order());
	      //为根菜单设置子菜单，getClild是递归调用的
	      for (MenuDto nav : rootMenu) {
	        /* 获取根节点下的所有子节点 使用getChild方法*/
	        List<MenuDto> childList = getChild(nav.getMenuCode(), allMenuList);
	        nav.setChildren(childList);//给根节点设置子节点
	      }
	        
        return rootMenu;
    }
    
    /**
     * 获取子节点
     * @param id 父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<MenuDto> getChild(String menuCode,List<MenuDto> allMenuList){
      //子菜单
      List<MenuDto> childList = new ArrayList<MenuDto>();
      for (MenuDto nav : allMenuList) {
        //遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
        //相等说明：为该根节点的子节点。
        if(nav.getSuperiorMenu().equals(menuCode)){
          childList.add(nav);
        }
      }
      //递归
      for (MenuDto nav : childList) {
        nav.setChildren(getChild(nav.getMenuCode(), allMenuList));
      }
      Collections.sort(childList,order());//排序
      //如果节点下没有子节点，返回一个空List（递归退出）
      if(childList.size() == 0){
        return new ArrayList<MenuDto>();
      }
      return childList;
    }
    
    /*
     * 排序,根据order排序
     */
    public Comparator<MenuDto> order(){
      Comparator<MenuDto> comparator = new Comparator<MenuDto>() {
        @Override
        public int compare(MenuDto o1, MenuDto o2) {
          if(o1.getMenuSerial() != o2.getMenuSerial()){
            return o1.getMenuSerial() - o2.getMenuSerial();
          }
          return 0;
        }
      };
      return comparator;
    }
    
    public UserDto selectUserById(Integer userId) {
        StringBuffer selectUserByParamSql = new StringBuffer("select pk_user_id, user_code,user_name,user_mobile,user_email,superior,create_user,update_user,inserttime,updatetime ,isactive from  tb_user where 1 = 1");
        UserDto userDtor=null;
        List<UserDto> userList = new ArrayList<UserDto>();
        if (userId != null ) {
            selectUserByParamSql.append(" and pk_user_id = ").append(userId).append(" ");
        }

        mysqlJdbcTemplate.query(selectUserByParamSql.toString(), new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                UserDto user = new UserDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), DateUtil.formatDate(rs.getString(9)), DateUtil.formatDate(rs.getString(10)), rs.getByte(11));
                userList.add(user);
            }
        });
        
        if(userList.size()>0) {
        	userDtor=userList.get(0);
        }
        
        return userDtor;
    }
    

    public List<UserDto> selectUserByParam(UserDto userDto) {
        StringBuffer selectUserByParamSql = new StringBuffer("select pk_user_id, user_code,user_name,user_mobile,user_email,superior,create_user,update_user,inserttime,updatetime ,isactive from  tb_user where 1 = 1");
        List<UserDto> userList = new ArrayList<UserDto>();
        if (userDto.getPkUserId() != null && userDto.getPkUserId() > 0) {
            selectUserByParamSql.append(" and pk_user_id = ").append(userDto.getPkUserId()).append(" ");
        }
        if (StringUtils.isNotBlank(userDto.getUserCode())) {
            selectUserByParamSql.append(" and user_code = '").append(userDto.getUserCode()).append("' ");
        }
        if (StringUtils.isNotBlank(userDto.getUserName())) {
            selectUserByParamSql.append(" and user_name like '%").append(userDto.getUserName()).append("%' ");
        }
        if (StringUtils.isNotBlank(userDto.getSuperior())) {
            selectUserByParamSql.append(" and superior like '%").append(userDto.getSuperior()).append("%' ");
        }
        if (userDto.getIsActive() != null) {
            selectUserByParamSql.append(" and isactive = ").append(userDto.getIsActive());
        }

        mysqlJdbcTemplate.query(selectUserByParamSql.toString(), new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                UserDto user = new UserDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), DateUtil.formatDate(rs.getString(9)), DateUtil.formatDate(rs.getString(10)), rs.getByte(11));
                userList.add(user);
            }
        });
        return userList;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    public ResultResponse insertUser(UserDto user) {
        // 判断是否已注册
        int count = mysqlJdbcTemplate.queryForObject(selectUserIgnoreActiveByCodeSql, Integer.class, user.getUserCode());
        if (count != 0) {
            return new ResultResponse(false, ReponseEnum.USER_EXIST.getResCode(),
                    ReponseEnum.USER_EXIST.getResMsg());
        }
        //新加用户的角色关系到数据库
        UserRoleDto userRoleDto =new UserRoleDto();
        userRoleDto.setUserCode(user.getUserCode());
        userRoleDto.setUserName(user.getUserCode());
        userRoleDto.setRoleCode(user.getRoleCode());
        //先删除，再新增
        userRoleDataAccess.deleteUserRoleByUserCode(userRoleDto);
        userRoleDataAccess.insertUserRole(userRoleDto);

        // 注册
        int i = mysqlJdbcTemplate.update(createUserSql, new Object[]{user.getUserCode(), user.getUserName(), user.getUserPassword(),
                user.getUserMobile(), user.getUserEmail(), user.getSuperior()});


        return i == 1 ? new ResultResponse(true, ReponseEnum.SUCCEED.getResCode(), ReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, ReponseEnum.FAIL.getResCode(), ReponseEnum.FAIL.getResMsg());
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    public ResultResponse updateUser(UserDto user) {
    	
        //修改用户的角色关系到数据库
        UserRoleDto userRoleDto =new UserRoleDto();
        userRoleDto.setUserCode(user.getUserCode());
        userRoleDto.setUserName(user.getUserCode());
        userRoleDto.setRoleCode(user.getRoleCode());
        //先删除，再新增
        userRoleDataAccess.deleteUserRoleByUserCode(userRoleDto);
        userRoleDataAccess.insertUserRole(userRoleDto);
    	
        StringBuffer updateUserSql = new StringBuffer("update tb_user set ");
        // USER_PASSWORD = ?,USER_MOBILE = ?,USER_EMAIL = ?,SUPERIOR = ? WHERE USER_CODE = ? AND ISACTIVE = 1
        if (StringUtils.isNotBlank(user.getUserPassword())) {
            updateUserSql.append(" user_password = '").append(user.getUserPassword()).append("',");
        }
        if (StringUtils.isNotBlank(user.getUserMobile())) {
            updateUserSql.append(" user_mobile = '").append(user.getUserMobile()).append("',");
        }
        if (StringUtils.isNotBlank(user.getUserEmail())) {
            updateUserSql.append(" user_email = '").append(user.getUserEmail()).append("',");
        }
        if (StringUtils.isNotBlank(user.getSuperior())) {
            updateUserSql.append(" superior = '").append(user.getSuperior()).append("',");
        }
        if (user.getIsActive() != null) {
            updateUserSql.append(" isactive = ").append(user.getIsActive()).append(",");
        }
        if (!updateUserSql.toString().trim().endsWith(",")) {
            return new ResultResponse(false, ReponseEnum.FAIL.getResCode(), ReponseEnum.FAIL.getResMsg());
        }
        updateUserSql.deleteCharAt(updateUserSql.length() - 1).append(" where user_code = '").append(user.getUserCode()).append("'");

        int i = mysqlJdbcTemplate.update(updateUserSql.toString());

        return i == 0 ? new ResultResponse(false, ReponseEnum.FAIL.getResCode(), ReponseEnum.FAIL.getResMsg())
                : new ResultResponse(true, ReponseEnum.SUCCEED.getResCode(), ReponseEnum.SUCCEED.getResMsg());
    }

    /**
     * 删除用户 根据用户编号
     *
     * @return
     */
    public ResultResponse deleteUser(UserDto user) {
        int i = mysqlJdbcTemplate.update(deleteUserSql, new Object[]{user.getPkUserId()});

        return i == 1 ? new ResultResponse(true, ReponseEnum.SUCCEED.getResCode(), ReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, ReponseEnum.FAIL.getResCode(), ReponseEnum.FAIL.getResMsg());
    }

    /**
     * 用户登录
     *
     * @return
     */
    public LoginResponse login(String userCode, String password) {
    	//检查用户是否存在
        Integer count = mysqlJdbcTemplate.queryForObject(selectUserByCodeSql, Integer.class, userCode);
        if (count == 0) {
            return new LoginResponse(false, ReponseEnum.USER_NOT_EXIST.getResCode(), ReponseEnum.USER_NOT_EXIST.getResMsg());
        }
        //检查用户密码是否正确
        Integer i = mysqlJdbcTemplate.queryForObject(selectUserByCodePasswordSql, Integer.class, new Object[]{userCode, password});
        if (i == 0) {
            return new LoginResponse(false, ReponseEnum.USER_PWD_ERROR.getResCode(),
                    ReponseEnum.USER_PWD_ERROR.getResMsg());
        }
        //到这里，用户已经登录成功，检查用户角色
        Integer j = mysqlJdbcTemplate.queryForObject(selectUserIsAdmin, Integer.class, new Object[]{userCode});
        String isAdmin="0";//默认不是admin角色
        if (j > 0) {
        	isAdmin="1";
        }
        LoginResponse loginResponse=new LoginResponse(true, ReponseEnum.SUCCEED.getResCode(), ReponseEnum.SUCCEED.getResMsg());
        loginResponse.setIsAdmin(isAdmin);
        return loginResponse;
    }
    
    public boolean isAdmin(String userCode) {
    	boolean isAdmin=false;//默认不是admin角色
    	//到这里，用户已经登录成功，检查用户角色
        Integer j = mysqlJdbcTemplate.queryForObject(selectUserIsAdmin, Integer.class, new Object[]{userCode});
        if (j > 0) {
        	isAdmin=true;
        }
        return isAdmin;
    }

    public ResultResponse deleteByIds(String[] ids) {

        String deleteUserByIdsSql = "UPDATE TB_USER SET ISACTIVE = 0 WHERE PK_USER_ID IN (";
        for (int i = 0; i < ids.length - 1; i++) {
            deleteUserByIdsSql = deleteUserByIdsSql + ids[i] + " ,";
        }
        deleteUserByIdsSql = deleteUserByIdsSql + ids[ids.length - 1] + " )";

        int i = mysqlJdbcTemplate.update(deleteUserByIdsSql);

        return i >= 1 ? new ResultResponse(true, ReponseEnum.SUCCEED.getResCode(), ReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, ReponseEnum.FAIL.getResCode(), ReponseEnum.FAIL.getResMsg());
    }

    public ResultResponse openByIds(String[] ids) {
        String deleteUserByIdsSql = "UPDATE TB_USER SET ISACTIVE = 1 WHERE PK_USER_ID IN (";
        for (int i = 0; i < ids.length - 1; i++) {
            deleteUserByIdsSql = deleteUserByIdsSql + ids[i] + " ,";
        }
        deleteUserByIdsSql = deleteUserByIdsSql + ids[ids.length - 1] + " )";

        int i = mysqlJdbcTemplate.update(deleteUserByIdsSql);

        return i >= 1 ? new ResultResponse(true, ReponseEnum.SUCCEED.getResCode(), ReponseEnum.SUCCEED.getResMsg())
                : new ResultResponse(false, ReponseEnum.FAIL.getResCode(), ReponseEnum.FAIL.getResMsg());

    }
}
