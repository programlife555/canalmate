package com.ppdai.canalmate.api.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ppdai.canalmate.api.controller.canal.server.CanalProcessController;
import com.ppdai.canalmate.api.dataaccess.auth.UserDataAccess;
import com.ppdai.canalmate.api.dataaccess.auth.UserRoleDataAccess;
import com.ppdai.canalmate.api.entity.dto.UserDto;
import com.ppdai.canalmate.api.entity.dto.UserRoleDto;
import com.ppdai.canalmate.api.entity.dto.canal.CanalGapDto;
import com.ppdai.canalmate.api.model.canal.server.CanalClientStatus;
import com.ppdai.canalmate.api.model.canal.server.CanalServerConfig;
import com.ppdai.canalmate.api.model.canal.server.CanalServerConfigShow;
import com.ppdai.canalmate.api.model.canal.server.CanalWarn;
import com.ppdai.canalmate.api.model.canal.server.TbConfig;
import com.ppdai.canalmate.api.service.canal.CanalGapService;
import com.ppdai.canalmate.api.service.canal.JdbcTemplateService;
import com.ppdai.canalmate.api.service.canal.ProcessMonitorService;
import com.ppdai.canalmate.api.service.canal.server.CanalServerConfigService;
import com.ppdai.canalmate.api.service.canal.server.CanalWarnService;
import com.ppdai.canalmate.api.service.canal.server.TbConfigService;
import com.ppdai.canalmate.common.cons.CanalConstants;
import com.ppdai.canalmate.common.mail.Mail;
import com.ppdai.canalmate.common.model.ZKDestinationBean;
import com.ppdai.canalmate.common.utils.CanalPropertyUtils;
import com.ppdai.canalmate.common.utils.CanalZKUtils;
import com.ppdai.canalmate.common.utils.DateUtil;
import com.ppdai.canalmate.common.utils.P;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ScheduledJob {
    Logger logger = LoggerFactory.getLogger(ScheduledJob.class);


    @Qualifier(value = "processMonitorService")
    @Autowired
    private ProcessMonitorService processMonitorService;

    @Autowired
    private CanalWarnService canalWarnService;

    @Qualifier(value = "canalGapService")
    @Autowired
    CanalGapService canalGapService;

    @Autowired
    TbConfigService tbConfigService;


    @Autowired
    UserRoleDataAccess userRoleDataAccess;

    @Autowired
    UserDataAccess userDataAccess;

    private static final SimpleDateFormat DECIMAL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Integer count0 = 0;
    public final static long ONE_MINUTE = 60 * 1000;
    public final static long CHECK_INTERVAL_MINUTE = 10 * ONE_MINUTE; //每10分钟调度一次检查进程

    /* 间隔：每隔 fixedRate 时间 ，该程序就出发一次。
     * 作用：监控canal server 和canal client,client gap的进程状态，并把信息封装成canalWarn，发送到管理员角色的用户的邮箱中。
     * */
    @Scheduled(fixedRate = CHECK_INTERVAL_MINUTE)
    public void checkServerStatus() throws InterruptedException {
        logger.info(String.format("---第%s次调度执行开始，当前时间为：%s", count0++, DECIMAL_FORMAT.format(new Date())));
        long t1=System.currentTimeMillis();
        List<CanalWarn> canalWarnList = new ArrayList<CanalWarn>();

        List<CanalServerConfigShow> canalServerConfigShowList = new ArrayList<CanalServerConfigShow>();
        Map<String, String> argsMap = new HashMap<String, String>();
        argsMap.put("pageNum", "1");
        argsMap.put("numberPerPage", "1000");//取第一页，每页显示1000个，目的是全部取到所有的canal server

        //从数据库中查询所有的canalserver
        Map map = processMonitorService.listCanalServerConfig(argsMap);
        canalServerConfigShowList = (List<CanalServerConfigShow>) map.get("resultList");
//        P.pr("canalServerConfigShowList.size():"+canalServerConfigShowList.size());
        //重新封装从数据库中的这个list，根据zk的状态，填入状态信息
        canalServerConfigShowList = processMonitorService.getCanalServerStatusBeanList(canalServerConfigShowList);

        /*循环所有的Server状态。针对每一个server
         * 1：先检查server的状态，并封装信息为报警信息
         * 2：检查该server下的所有client信息，并封装为报警信息
         * 3：检查该server下的所有client gap信息，并封装为报警信息
         */
        for (CanalServerConfigShow canalServerConfigShow : canalServerConfigShowList) {
            String serverStatusCode = canalServerConfigShow.getServerStatusCode();
            String standbyStatusCode = canalServerConfigShow.getStandbyStatusCode();
            String masterCanalIp = canalServerConfigShow.getCanalServerHost();
            String standbyCanalIp= canalServerConfigShow.getStandbyServerHost();

            //检查master server的状态
            String canalServerWarn = "canal server:" + masterCanalIp;
            if (serverStatusCode.equals(CanalConstants.RUNNING)) {
                logger.debug(canalServerWarn + " RUNNING");
            } else {
                logger.debug(canalServerWarn + " NOT RUNNING");
                String message = canalServerWarn + " NOT RUNNING";
                CanalWarn canalWarn = new CanalWarn();
                canalWarn.setWarnType("canal_server告警");
                canalWarn.setWarnTime(new Date());
                canalWarn.setWarnMessage(message);
                canalWarnService.insertDefaultCanalWarn(canalWarn);
                String member = JSON.toJSONString(canalWarn);
                logger.debug(member);

                canalWarnList.add(canalWarn);
            }
            //检查standby server的状态
            String standbyServerWarn = "canal standby server:" + standbyCanalIp;
            if (standbyStatusCode.equals(CanalConstants.RUNNING)) {
                logger.debug(standbyServerWarn + " RUNNING");
            } else {
                String message = standbyServerWarn + " NOT RUNNING";
                CanalWarn canalWarn = new CanalWarn();
                canalWarn.setWarnType("canal_server告警");
                canalWarn.setWarnTime(new Date());
                canalWarn.setWarnMessage(message);
                canalWarnService.insertDefaultCanalWarn(canalWarn);
                String member = JSON.toJSONString(canalWarn);
                logger.debug(member);

                canalWarnList.add(canalWarn);

            }

            /*查看该server下的所有的client状态
             * */
            //先从zk里面取该server下所有destination的状态信息，给后面的判断client状态准备数据
            Long canalId = canalServerConfigShow.getId();
            CanalServerConfig canalServerConfig = processMonitorService.selectCanalServerConfigByPrimaryKey(canalId);
            String zkAddress = CanalPropertyUtils.getPropertyValueByKey(canalServerConfig.getCanalServerConfiguration(), "canal.zkServers");
            List<ZKDestinationBean> zKDestinationBeanList = CanalZKUtils.getZKDestinationBeanListFromZKAddress(zkAddress);
            //判断该server下所有canal client的状态
            List<CanalClientStatus> canalClientStatusList = processMonitorService.getCanalClientStatusBeanList(String.valueOf(canalId), zKDestinationBeanList);
            for (CanalClientStatus canalClientStatus : canalClientStatusList) {
                String canalClientStatusCode = canalClientStatus.getCanalClientStatusCode();
                String message = canalClientStatus.getComment();

                if (CanalConstants.RUNNING.equals(canalClientStatusCode)) {
                    logger.debug(message);
                } else {
                    logger.debug(message);

                    CanalWarn canalWarn = new CanalWarn();
                    canalWarn.setWarnType("canal_客户端告警");
                    canalWarn.setWarnTime(new Date());
                    canalWarn.setWarnMessage(message);
                    canalWarnService.insertDefaultCanalWarn(canalWarn);
                    String member = JSON.toJSONString(canalWarn);
                    logger.debug(member);

                    canalWarnList.add(canalWarn);
                }

            }

            //判断该server下的所有destination(instance)的延迟
            List<CanalGapDto> instanceGapList = canalGapService.listInstanceStatus(String.valueOf(canalId));

            for (CanalGapDto canalGapDto : instanceGapList) {
                String gapColour = canalGapDto.getColour();
                //若不为绿色，则说明是红色或者黄色，说明有问题
                String message = "";
                if (!gapColour.equals(CanalConstants.GREEN)) {
                    message = canalGapDto.getComment();
                    String mysqlAddress = canalGapDto.getMasterAddress();
                    logger.debug(message);
                    CanalWarn canalWarn = new CanalWarn();
                    canalWarn.setWarnType("canal_延迟");
                    canalWarn.setWarnTime(new Date());
                    canalWarn.setWarnMessage("mysql地址：" + mysqlAddress + "对应的" + message);
                    canalWarnService.insertDefaultCanalWarn(canalWarn);
                    String member = JSON.toJSONString(canalWarn);
                    logger.debug(member);

                    canalWarnList.add(canalWarn);
                }
            }


        }


        if(canalWarnList==null||canalWarnList.size()==0) {
            logger.info("-----------------canal集群和canal client状态正常，无告警------");
        }else {
            logger.info("===============canal集群和canal client检测到状态异常，准备告警======");
        	sendMail(canalWarnList);
        }
        long t2=System.currentTimeMillis();

        logger.info(String.format("---第%s次执行调度结束，当前时间为：%s，调度耗时:%s s", count0++, DECIMAL_FORMAT.format(new Date()),(t2-t1)/1000));
    }

    /*为了得到邮件合并的效果，对多个warn，合并为一个邮件。
     *
     * */
    private void sendMail(List<CanalWarn> canalWarnList) {
        CanalWarn mergeCanalWarn = new CanalWarn();//合并的canalWarn
        String title = "canal_warn";
        StringBuilder contentSb = new StringBuilder();
        for (CanalWarn canalWarn : canalWarnList) {
            contentSb.append("告警类型 : " + canalWarn.getWarnType());
            contentSb.append("\r\n");
            contentSb.append("告警内容 : " + canalWarn.getWarnMessage());
            contentSb.append("\r\n");
            contentSb.append("告警时间 : " + DateUtil.DateToStr(canalWarn.getWarnTime()));
            contentSb.append("\r\n\r\n");
        }
        
        mergeCanalWarn.setWarnType(title);
        mergeCanalWarn.setWarnMessage(contentSb.toString());

        //从数据库中找是否发邮件的标识
        String configName = "is_send_mail";
        TbConfig tbConfig = tbConfigService.selectByConfigName(configName);
        String isSendEmail = tbConfig.getConfigValue();
        if (StringUtils.isNotBlank(isSendEmail) && isSendEmail.equals("1") && StringUtils.isNotBlank(contentSb.toString())) {
            logger.info("=====开启了发邮件标志，现在发送邮件");
            logger.info("邮件内容如下：\r\n" + contentSb.toString());
            sendMail(mergeCanalWarn);
        } else {
        	if(canalWarnList!=null&&canalWarnList.size()>0) {
                logger.info("======没有开启发邮件标志，在后台打印邮件内容，不发送");
                logger.info("邮件内容如下：\r\n" + contentSb.toString());
            }
        }

    }


    private void sendMail(CanalWarn canalWarn) {
        //获取邮件title
        String title = canalWarn.getWarnType();
        //获取邮件content
        String content = canalWarn.getWarnMessage();

        if(StringUtils.isBlank(content)) {
        	logger.info("=====邮件内容为空，不发送：content："+content);
        	return;
        }

        
        //获取邮件收件人列表，找到系统管理员角色对应的用户的email
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setRoleCode("1");//系统管理员的roleid=1;
        List<UserRoleDto> userRoleList = userRoleDataAccess.selectRole(userRoleDto);
        StringBuilder emails=new StringBuilder();
        //群发的收件人addressStrT
        List<String> addrList = new ArrayList<String>();
        for (UserRoleDto userRole : userRoleList) {
            String userCode = userRole.getUserCode();
            String userName=userRole.getUserName();
            UserDto userDto=new UserDto();
            userDto.setUserCode(userCode);
            userDto.setUserName(userName);
            List<UserDto> userDtoList = userDataAccess.selectUserByParam(userDto);
            if (userDtoList != null && userDtoList.size()>0) {
            	UserDto adminUserDto=userDtoList.get(0);//默认取第一个，认为userCode和userName 取唯一值
                String email = adminUserDto.getUserEmail();
                if (StringUtils.isNotBlank(email)) {
                    addrList.add(email);
                    emails.append(email+",");
                }else {
                    logger.error("====对应管理员的邮箱为空，userCode："+userCode+",userName:"+userName);

                }
            }else {
                logger.error("=====userCode："+userCode+",userName:"+userName+" 对应的tb_user表中没记录");
            }
        }
        int size = addrList.size();
        String[] addressStrT = (String[]) addrList.toArray(new String[size]);
        logger.info("=====收件人邮箱列表："+emails.toString());

        //发邮件
        Mail.doSendMail(title, content, addressStrT);
    }

}
