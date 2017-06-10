package edu.columbia.cuitei.deptdir.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.cas.ServiceProperties;

import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import javax.sql.DataSource;

import org.jasig.cas.client.validation.Saml11TicketValidator;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    // @Autowired private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired private DataSource dataSource;

    // @Value("${spring.queries.users-query}") private String usersQuery;
    // @Value("${spring.queries.roles-query}") private String rolesQuery;

    @Value("${cas.server}")
    private String casServer;

    @Value("${host.name}")
    private String hostName;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    /*
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
    */
    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("cas.server={}", casServer);
        http
                .authorizeRequests()
                .antMatchers("/api/deptdir/search/").permitAll()
                .antMatchers("/api/deptdir/search/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/admin/home")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }
    */

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/")
                .permitAll()

                .antMatchers("/api/deptdir/search/").permitAll()
                .antMatchers("/api/deptdir/search/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/search/**?").permitAll()
                .antMatchers("/search/**").permitAll()
                .antMatchers("/search.html").permitAll()

                .antMatchers("/index.html").permitAll()

                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")

                .antMatchers("/login", "/logout", "/admin/home").authenticated().antMatchers("/filtered")
                .hasAuthority("ADMIN").anyRequest().authenticated();

        http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint());
        http.authenticationProvider(casAuthenticationProvider());
        http.addFilter(casAuthenticationFilter());

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(casAuthenticationProvider());
    }

    /*
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }
    */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars");
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }


    @Bean
    public ServiceProperties serviceProperties() {
        log.info("hostName={}", hostName);
        final ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(hostName + "/j_spring_cas_security_check");
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    /*
    @Bean
    public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> customUserDetailsService() {
        return new UserDetailsService();
    }
    */
    @Bean
    public UserDetailsService customUserDetailsService() {
        return new UserDetailsService(jdbcTemplate());
    }

    @Bean
    public Saml11TicketValidator casSamlServiceTicketValidator() {
        return new Saml11TicketValidator(casServer + "/cas");
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        final CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        // casAuthenticationProvider.setAuthenticationUserDetailsService(customUserDetailsService());
        casAuthenticationProvider.setUserDetailsService(customUserDetailsService());
        casAuthenticationProvider.setTicketValidator(casSamlServiceTicketValidator());
        casAuthenticationProvider.setKey("an_id_for_this_auth_provider_only");
        return casAuthenticationProvider;
    }

    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        final String loginUrl = casServer + "/cas/login";
        log.info("casAuthenticationEntryPoint - loginUrl={}", loginUrl);
        final CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(loginUrl);
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }

    /*
    @Bean
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(casAuthenticationProvider());
    }
    */

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {

        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        casAuthenticationFilter.setFilterProcessesUrl("/j_spring_cas_security_check");
        casAuthenticationFilter.setSessionAuthenticationStrategy(sessionStrategy());
        return casAuthenticationFilter;
    }

    @Bean
    public SessionAuthenticationStrategy sessionStrategy() {
        return new SessionFixationProtectionStrategy();
    }

}
