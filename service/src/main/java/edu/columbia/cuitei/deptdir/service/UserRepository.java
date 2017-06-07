package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUni(String uni);
}
