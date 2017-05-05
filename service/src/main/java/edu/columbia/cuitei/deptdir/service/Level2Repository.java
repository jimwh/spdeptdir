package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level2;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface Level2Repository extends JpaRepository<Level2, Integer> {
    List<Level2> findByDirectoryName(String name);
}
