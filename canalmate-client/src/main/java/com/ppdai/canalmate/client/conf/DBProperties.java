package com.ppdai.canalmate.client.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBProperties {
    
    @Value("${spring.datasource.url")
    private String url;
    @Value("${spring.datasource.driver-class-name")
    private String name;
    @Value("${spring.datasource.username")
    private String user;
    @Value("${spring.datasource.password")
    private String password;
    
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
