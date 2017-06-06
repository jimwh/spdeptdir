package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findAllByRole(String role);
}
