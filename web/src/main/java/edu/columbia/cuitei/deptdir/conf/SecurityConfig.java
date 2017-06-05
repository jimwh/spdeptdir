package edu.columbia.cuitei.deptdir.conf;

import edu.columbia.cuitei.deptdir.error.MyAccessDeniedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
// http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
// Switch off the Spring Boot security configuration
// @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;
    //@Resource private MyAccessDeniedHandler accessDeniedHandler;

    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index", "/api/deptdir/search/**", "/about", "amend/list", "/amend/list", "amend/**", "/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
    }

    // Spring Boot configured this already.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars");
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}
