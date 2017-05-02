package edu.columbia.cuitei.deptdir.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"edu.columbia.cuitei.deptdir"})
@EntityScan(basePackages = {"edu.columbia.cuitei.deptdir"})
@ComponentScan(basePackages = {"edu.columbia.cuitei.deptdir"})
@EnableAutoConfiguration(exclude = {LiquibaseAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
