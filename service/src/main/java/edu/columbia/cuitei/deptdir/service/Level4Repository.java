package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level4;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface Level4Repository extends JpaRepository<Level4, Integer> {
    List<Level4> findByName(String name);
    List<Level4> findAllByParentIn(List<Integer>list);
    List<Level4> findAllByParent(Integer parent);
}
