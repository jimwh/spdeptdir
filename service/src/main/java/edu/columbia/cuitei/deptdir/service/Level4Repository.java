package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level4;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Level4Repository extends CrudRepository<Level4, Integer> {
    List<Level4> findAllByDirectoryName(String name);
}
