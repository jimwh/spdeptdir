package edu.columbia.cuitei.deptdir.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Level4Repository extends JpaRepository<Level4, Integer> {
    List<Level4> findByName(String name);
    List<Level4> findAllByParentIn(List<Integer>list);
    List<Level4> findAllByParent(Integer parent);
}
