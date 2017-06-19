package edu.columbia.cuitei.deptdir.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsService extends JdbcDaoImpl {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    public UserDetailsService(final JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
        log.info("username={}", username);
        final List<UserDetails> users = loadUsersByUsername(username);
        if ( users.isEmpty() ) {
            throw new UsernameNotFoundException("Username: "+ username + " not found.");
        }
        // updateLastLoginDate(username);
        final UserDetails user = users.get(0);
        log.info("user={}", user.toString());

        final List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        if (getEnableAuthorities()) {
            grantedAuthorityList.addAll(loadUserAuthorities(user.getUsername()));
        }
        if (getEnableGroups()) {
            grantedAuthorityList.addAll(loadGroupAuthorities(user.getUsername()));
        }
        if (grantedAuthorityList.isEmpty()) {
            throw new UsernameNotFoundException(
                    messages.getMessage("JdbcDaoImpl.noAuthority",
                            new Object[] { username }, "User {0} has no GrantedAuthority"
                    )
            );
        }
        log.info("grantedAuthorityList={}", grantedAuthorityList.toString());
        return createUserDetails(username, user, grantedAuthorityList);
    }

}