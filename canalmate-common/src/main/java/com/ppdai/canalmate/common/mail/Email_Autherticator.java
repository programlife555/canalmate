package com.ppdai.canalmate.common.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Email_Autherticator extends Authenticator {
    String username = new String();
	String password = new String();
	
	public Email_Autherticator() {
		super();
	}
	public Email_Autherticator(String user,String pwd){
		super();
		username = user;
		password = pwd;
	}
	
	@Override
	public PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(username,password);
	}
}
