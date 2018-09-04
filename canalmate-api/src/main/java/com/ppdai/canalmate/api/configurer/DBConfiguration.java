package com.ppdai.canalmate.api.configurer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

import javax.sql.DataSource;


@Configuration
public class DBConfiguration {

    @Bean(name = "mysqlDataSource")
    @Qualifier("mysqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource mysqlDataSource() {
        DataSource build = DataSourceBuilder.create().build();
//        try {
//			build.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
        org.apache.tomcat.jdbc.pool.DataSource typedDS = (org.apache.tomcat.jdbc.pool.DataSource) build;
        typedDS.setValidationQuery("SELECT 1");
        typedDS.setTestOnBorrow(true);
        return build;
    }

    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
