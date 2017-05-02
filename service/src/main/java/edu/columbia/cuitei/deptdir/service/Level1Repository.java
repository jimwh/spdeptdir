package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level1;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface Level1Repository extends JpaRepository<Level1, Integer> {
    List<Level1> findAllByDirectoryName(String name);
}
