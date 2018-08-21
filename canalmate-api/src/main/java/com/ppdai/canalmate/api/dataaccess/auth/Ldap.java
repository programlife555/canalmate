package com.ppdai.canalmate.api.dataaccess.auth;
//package com.ppdai.console.dataaccess.auth;
//
//import com.ppdai.console.api.entity.dto.UserDto;
//import com.ppdai.console.api.entity.dto.AlarmScript.AlarmScriptDictDto;
//import com.ppdai.console.common.utilities.MD5Encrypt;
//import com.ppdai.data.goldeye.dashboard.domain.dataaccess.alarmScript.AlarmScriptTypedictDataAccess;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//import javax.naming.Context;
//import javax.naming.NamingEnumeration;
//import javax.naming.NamingException;
//import javax.naming.directory.Attribute;
//import javax.naming.directory.Attributes;
//import javax.naming.directory.SearchControls;
//import javax.naming.directory.SearchResult;
//import javax.naming.ldap.InitialLdapContext;
//import javax.naming.ldap.LdapContext;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicReference;
//
///**
// * Created by gongyun on 2017/12/26.
// */
//@Component
//public class Ldap {
//    private final static String apolloPass = "PPDpc!123";
//    private AtomicReference<String> adServer = new AtomicReference<>();
//    private AtomicReference<String[]> searchBase = new AtomicReference<String[]>();
//
//    @Qualifier(value = "userDataAccess")
//    @Autowired
//    UserDataAccess userDataAccess;
//
//    @Autowired
//    private AlarmScriptTypedictDataAccess alarmScriptTypedictDataAccess;
//    public Ldap() {
//    }
//
//    private void doInitParm() {
////        String adServerTemp = "LDAP://corp.ppdapi.com";
//        String adServerTemp = "LDAP://10.128.105.172";
//        AlarmScriptDictDto alarmScriptDictDto = new AlarmScriptDictDto();
//        adServer.set(adServerTemp);
//        String searchBaseTemp = "OU=全真教（研发中心）,OU=拍拍贷总公司,DC=corp,DC=ppdai,DC=com|OU=道教（市场）,OU=拍拍贷总公司,DC=corp,DC=ppdai,DC=com" +
//                "|OU=天地会（风险中心）,OU=拍拍贷总公司,DC=corp,DC=ppdai,DC=com|OU=明教（产品中心）,OU=拍拍贷总公司,DC=corp,DC=ppdai,DC=com" +
//                "|OU=锦衣卫（大数据与人工智能）,OU=拍拍贷总公司,DC=corp,DC=ppdai,DC=com|OU=侠客岛（创新业务）,OU=拍拍贷总公司,DC=corp,DC=ppdai,DC=com" +
//                "|OU=test,OU=拍拍贷总公司,DC=corp,DC=ppdai,DC=com|OU=昆仑教（资产业务）,OU=拍拍贷总公司,DC=corp,DC=ppdai,DC=com|OU=青龙会（资产保全）,OU=拍拍贷总公司,DC=corp,DC=ppdai,DC=com" +
//                "|OU=铁掌帮（信息技术）,OU=拍拍贷总公司,DC=corp,DC=ppdai,DC=com"+"|OU=拍拍贷总公司,DC=corp,DC=ppdai,DC=com";
//        String[] searchBaseTemp1 = searchBaseTemp.split("\\|");
//        searchBase.set(searchBaseTemp1);
//    }
//
//
//    public boolean login(String username, String password) {
//        doInitParm();
//        if (username.equals("admin")) {
//            if (password.equals("admin123")) {
//                return true;
//            } else {
//                return false;
//            }
//        }else if (username.equals("ppdai")) {
//            if (password.equals("123456")) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        String[] searchBase1 = searchBase.get();
//        for (String serverPath : searchBase1) {
//            if (doLogin(username, password, serverPath)) {
//                return true;
//            }
////            try {
////                Thread.sleep(3);
////            } catch (InterruptedException e) {
////            }
//        }
//        return false;
//    }
//
//    private boolean doLogin(String username, String password, String serverPath) {
//        try {
//            Properties env = new Properties();
//            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//            env.put(Context.SECURITY_AUTHENTICATION, "simple");
//            env.put(Context.SECURITY_PRINCIPAL, "corp\\" + username);
//            env.put(Context.SECURITY_CREDENTIALS, password);
//            env.put(Context.PROVIDER_URL, adServer.get());
//
//            LdapContext ctx = new InitialLdapContext(env, null);
//            SearchControls searchCtls = new SearchControls();
//            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//
//            String searchFilter = String.format("(&(objectCategory=person)(objectClass=user)(SAMAccountName=%s))",
//                    username);
//
//            String returnedAtts[] = {"memberOf", "sAMAccountName", "cn", "distinguishedName", "mail"};
//            searchCtls.setReturningAttributes(returnedAtts);
//            NamingEnumeration<SearchResult> answer = ctx.search(serverPath, searchFilter, searchCtls);
//            if (answer.hasMoreElements()) {
//                SearchResult sr = (SearchResult) answer.next();
//                Attributes at = sr.getAttributes();
//                Attribute distinguishedName = at.get("distinguishedName");
//                String info = String.format("distinguishedName:%s,mail:%s,sAMAccountName:%s,cn:%s", getDValue(distinguishedName), getValue(at.get("mail")), getValue(at.get("sAMAccountName")), getValue(at.get("cn")));
//                UserDto userDto = new UserDto();
//                userDto.setUserCode(username);
//                List<UserDto> userDtos = userDataAccess.selectUserByParam(userDto);
//                if (userDtos == null || userDtos.size() < 1) {
//                    userDto.setUserName(username);
//                    userDto.setUserMobile("13999999999");
//                    userDto.setUserPassword(MD5Encrypt.generatePassword(password + "gold"));
//                    userDto.setUserEmail(getValue(at.get("mail")));
//                    try {
//                        userDataAccess.insertUser(userDto);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                System.out.println(info);
//                ctx.close();
//                return true;
//            }
//        } catch (NamingException e) {
//            // LOG.error(String.format("LDAP validate user %s error: %s",
//            // username, e.getMessage()), e);
//          e.printStackTrace();
//            return false;
//        }
//        return false;
//    }
//
//
//
//    private String getDValue(Attribute attribute) {
//        String value = getValue(attribute);
//        if (value.indexOf(",") > -1) {
//            value = value.split(",")[1];
//            value = value.replaceAll("OU=", "").trim();
//        }
//        return value;
//    }
//
//
//    private String getValue(Attribute attribute) {
//        if (attribute == null)
//            return "";
//        String value = attribute.toString();
//
//        if ("".equals(value))
//            return "";
//        if (value.indexOf(":") != -1) {
//            value = value.replaceAll(value.split(":")[0], "").trim();
//            value = value.substring(1, value.length()).trim();
//        }
//        return value;
//    }
//
//
//}
