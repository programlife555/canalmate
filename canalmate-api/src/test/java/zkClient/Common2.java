package zkClient;


import org.jasypt.util.text.BasicTextEncryptor;


public class Common2 {

	public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("PdCkDo3oYr0");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("app_canalmate");
        String password = textEncryptor.encrypt("jhL^cDj$rkWQOU@s$cF%");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
    }


}
