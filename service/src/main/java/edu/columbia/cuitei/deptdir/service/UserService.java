package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Role;
import edu.columbia.cuitei.deptdir.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void saveUser(final User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findAllByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
}
