package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level1;
import org.springframework.data.jpa.repository.JpaRepository;

interface Level1Repository extends JpaRepository<Level1, Integer> {
    Level1 findByDirectoryName(String name);
}
