package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level3;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface Level3Repository extends JpaRepository<Level3, Integer> {
    List<Level3> findAllByDirectoryName(String name);
}
