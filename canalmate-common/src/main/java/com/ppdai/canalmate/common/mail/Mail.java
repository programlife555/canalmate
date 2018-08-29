package com.ppdai.canalmate.common.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ppdai.canalmate.common.utils.P;
import com.ppdai.canalmate.common.utils.PropertiesUtils;


public class Mail
{
	
    // 邮件发送协议   
    private static String PROTOCOL;   
    // SMTP邮件服务器   
    private static String HOST;   
    // SMTP邮件服务器默认端口   
    private static String PORT;
    // 是否要求身份认证   
    private static String IS_AUTH;
    // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）   
    private static String IS_ENABLED_DEBUG_MOD;   
    // 发件人  
    private static String from;
    // 初始化连接邮件服务器的会话信息
    private static Properties props = null;   
    
    static {
    	
    	PROTOCOL=PropertiesUtils.getValue("PROTOCOL");
    	HOST=PropertiesUtils.getValue("HOST");
    	PORT=PropertiesUtils.getValue("PORT");
    	IS_AUTH=PropertiesUtils.getValue("IS_AUTH");
    	IS_ENABLED_DEBUG_MOD=PropertiesUtils.getValue("IS_ENABLED_DEBUG_MOD");
    	from=PropertiesUtils.getValue("from");

    	
        props = new Properties();   
        props.setProperty("mail.transport.protocol", PROTOCOL);   
        props.setProperty("mail.smtp.host", HOST);   
        props.setProperty("mail.smtp.port", PORT);   
        props.setProperty("mail.smtp.auth", IS_AUTH);   
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD);   
    }
	
  public static void main(String[] args)
  {

    try
    {
	  	//邮件标题和内容
	    String title="邮件测试_title";
	    String content="邮件测试_正文";
	    //群发的收件人
		List<String> addrList = new ArrayList<String>();  
		addrList.add("xxxxx@xxxx.com");
	
		int size = addrList.size();  
		String[] addressStrT = (String[])addrList.toArray(new String[size]);
		//发送邮件
		Mail.doSendMail(title, content, addressStrT);    
		}
    catch (Exception localException)
    {
    	
    }
  }

  public static int doSendMail(String titie, String content,String[] addressStrT)
  {
    int result = 1;
    try {
    	
//        PropertiesUtils p=new PropertiesUtils();
//        String smtp=p.getValue("smtp.ip");
//        String port=p.getValue("smtp.port");
//        String emaiFrom=p.getValue("smtp.from");
    	
//      String smtp="smtp.ppdaicorp.com";
//      String port="25";
//      String emaiFrom="";
//      
//      Properties props = new Properties();
//
//      props.put("mail.smtp.host", smtp);
//      props.put("mail.smtp.port", port);
//      props.put("mail.smtp.auth", "false");

      Authenticator auth = new Email_Autherticator();
      //System.out.println(props);
      Session session = Session.getDefaultInstance(props, auth);
      session.setDebug(false);

      MimeMessage message = new MimeMessage(session);
      // 设置邮件主题 
      message.setSubject(titie,"utf-8");
      // 设置纯文本内容为邮件正文 
      message.setText(content);
      // 设置html内容为邮件正文，指定MIME类型为text/html类型，并指定字符编码为gbk   
//      message.setContent("<span style='color:red;'>html邮件测试...</span>","text/html;charset=gbk");   

//      // 设置优先级(1:紧急   3:普通    5:低)   
//      message.setHeader("X-Priority", "1");   
//      // 要求阅读回执(收件人阅读邮件时会提示回复发件人,表明邮件已收到,并已阅读)   
//      message.setHeader("Disposition-Notification-To", from);
//      message.setHeader("Content-Type", "text/html;charset='gb2312'");
      message.setSentDate(new Date());
      message.saveChanges();
      Address address = new InternetAddress(from);
      message.setFrom(address);

      String[] addressStr = addressStrT;
      int len = addressStr.length;
      InternetAddress[] addressTo = new InternetAddress[len];
      for (int i = 0; i < len; i++) {
        addressTo[i] = new InternetAddress(addressStr[i]);
      }
      message.addRecipients(Message.RecipientType.TO, addressTo);

      Transport.send(message);
      P.print("Send Mail SUCCESS!");
    } catch (Exception e) {
      e.printStackTrace();
      result = 0;
    }
    return result;
  }
}