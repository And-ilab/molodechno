package com.cl932.rsmw.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:application.yaml"})
public class DatasourceConfig {
    @Value("${spring.msserver.datasource.jdbc-url}")
    String jdbcUrl;
    @Value("${spring.msserver.datasource.username}")
    String username;
    @Value("${spring.msserver.datasource.password}")
    String password;
    @Bean
    public DataSource msDataSource() {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(20);
        config.setConnectionTimeout(3000000);
        config.setConnectionTimeout(1200000);
        config.setLeakDetectionThreshold(3000000);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        config.setJdbcUrl(jdbcUrl);
        return new HikariDataSource(config);
    }

    @Value("${spring.datasource.jdbc-url}")
    String jdbcUrlBase;
    @Value("${spring.datasource.username}")
    String usernameBase;
    @Value("${spring.datasource.password}")
    String passwordBase;
    @Primary
    @Bean
    public DataSource defaultDataSource() {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(20);
        config.setConnectionTimeout(3000000);
        config.setConnectionTimeout(1200000);
        config.setLeakDetectionThreshold(3000000);
        config.setUsername(usernameBase);
        config.setPassword(passwordBase);
        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        config.setJdbcUrl(jdbcUrlBase);
        return new HikariDataSource(config);
    }
}