package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level3;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface Level3Repository extends JpaRepository<Level3, Integer> {
    List<Level3> findByName(String name);
    List<Level3> findAllByParentIn(List<Integer>list);

    List<Level3> findAllByParent(Integer parent);
}
