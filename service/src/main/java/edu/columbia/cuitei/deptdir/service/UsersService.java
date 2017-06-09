package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.AuthoritiesRepository;
import edu.columbia.cuitei.deptdir.domain.Users;
import edu.columbia.cuitei.deptdir.domain.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UsersService {
    @Resource private UsersRepository usersRepository;
    @Resource private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Users findUserByEmail(final String email) {
        return usersRepository.findByEmail(email);
    }
    public Users findUserByUsername(final String username) {
        return usersRepository.findByUsername(username);
    }

    @Transactional
    public void saveUser(final Users users){
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        users.setActive(true);
        // Authorities userRole = authoritiesRepository.findAllByAuthority("ADMIN");
        //users.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        usersRepository.save(users);
    }

}
