package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level2;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Level2Repository extends CrudRepository<Level2, Integer> {
    List<Level2> findAllByDirectoryName(String name);
}
