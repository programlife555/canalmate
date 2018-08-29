package com.ppdai.canalmate.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
    	ApplicationContext ctx=SpringApplication.run(Application.class, args);
    	String[] activeProfiles=ctx.getEnvironment().getActiveProfiles();
    	for (String profile : activeProfiles) {
            System.out.printf("Spring Boot 使用profile为:{}" , profile);  
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}

