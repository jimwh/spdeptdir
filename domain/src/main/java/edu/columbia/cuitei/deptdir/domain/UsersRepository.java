package edu.columbia.cuitei.deptdir.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
    Users findByEmail(String email);
}
