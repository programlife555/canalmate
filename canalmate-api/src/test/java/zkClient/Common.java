package zkClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ppdai.canalmate.common.utils.P;

public class Common {

	public static void main(String[] args) {
		String configProperty2=" ## mysql serverId\r\n" + 
				"canal.instance.mysql.slaveId=100\r\n" + 
				"# position info\r\n" + 
				"canal.instance.master.address=10.114.16.49:3306\r\n" + 
				"canal.instance.master.journal.name=\r\n" + 
				"canal.instance.master.position=\r\n" + 
				"canal.instance.master.timestamp=\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"# table meta tsdb info\r\n" + 
				"canal.instance.tsdb.enable=true\r\n" + 
				"canal.instance.tsdb.dir=${canal.file.data.dir:../conf}/${canal.instance.destination:}\r\n" + 
				"canal.instance.tsdb.url=jdbc:h2:${canal.instance.tsdb.dir}/h2;CACHE_SIZE=1000;MODE=MYSQL;\r\n" + 
				"#canal.instance.tsdb.url=jdbc:mysql://127.0.0.1:3306/canal_tsdb\r\n" + 
				"canal.instance.tsdb.dbUsername=canal\r\n" + 
				"canal.instance.tsdb.dbPassword=canal\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"#canal.instance.standby.address =\r\n" + 
				"#canal.instance.standby.journal.name =\r\n" + 
				"#canal.instance.standby.position = \r\n" + 
				"#canal.instance.standby.timestamp = \r\n" + 
				"# username/password\r\n" + 
				"canal.instance.dbUsername=canal\r\n" + 
				"canal.instance.dbPassword=canal\r\n" + 
				"canal.instance.defaultDatabaseName=test\r\n" + 
				"canal.instance.connectionCharset=UTF-8\r\n" + 
				"# table regex\r\n" + 
				"#canal.instance.filter.regex=.*\\\\..*\r\n" + 
				"canal.instance.filter.regex=testdb01\\\\.table.*\r\n" + 
				"# table black regex\r\n" + 
				"canal.instance.filter.black.regex=\r\n" + 
				"################################################# ";
		
//    	P.p(configProperty);
		String str=removeCommentLine(configProperty2);
//		P.p("=="+str);
		String key="canal.zkServers";
    	P.p("key:"+key);
    	Pattern p = Pattern.compile("(.*?)"+key+"(.*?)=(.*?)\r\n");
//    	Pattern p = Pattern.compile("(.*?)"+key+"(.*?)=(.*?)\r\n",Pattern.CASE_INSENSITIVE | Pattern.DOTALL); 
//    	Pattern p = Pattern.compile("(?<!#)(.*?)"+key+"(.*?)=(.*?)\r\n"); 
	    
        Matcher m = p.matcher(str);
//        P.p("m.groupCount():"+m.groupCount());
        while (m.find()) {
//        	P.p("000=="+m.group(0).trim());
//        	P.p("111=="+m.group(1).trim());
//        	P.p("222=="+m.group(2).trim());
        	P.p("333=="+m.group(3).trim());
//        	P.p("444=="+m.group(4).trim());
//        	P.p("555=="+m.group(5).trim());
//        	String line=m.group(3).trim();
//        	strs.append(line);
//        	P.p("====mache============:"+line);
        }
	}
	
	//去掉注释行
	public static String removeCommentLine(String str) {
        if(str!=null && !"".equals(str)) {  
        	//(?m)或MULTILINE代表启动多行模式,去掉#开头的行
        	String reg="(?m)^(#.*?)\r\n";
            Pattern p = Pattern.compile(reg);      
            Matcher m = p.matcher(str);    
//            while(m.find()) {
//            	P.p("===find:"+m.group(0).trim());
//            	P.p("111=="+m.group(1).trim());
//            }
            String strNoComment = m.replaceAll("");    
            
            return strNoComment;      
        }else {      
            return str;      
        }
	}

}
