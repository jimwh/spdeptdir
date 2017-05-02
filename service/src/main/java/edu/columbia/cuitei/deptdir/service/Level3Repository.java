package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level3;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Level3Repository extends CrudRepository<Level3, Integer> {
    List<Level3> findAllByDirectoryName(String name);
}
