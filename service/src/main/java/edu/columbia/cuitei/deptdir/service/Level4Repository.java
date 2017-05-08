package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level4;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

interface Level4Repository extends JpaRepository<Level4, Integer> {
    List<Level4> findByDirectoryName(String name);
    List<Level4> findAllByParentIn(List<Integer>list);
}
