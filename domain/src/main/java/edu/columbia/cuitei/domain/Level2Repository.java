package edu.columbia.cuitei.deptdir.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Level2Repository extends CrudRepository<Level1, Integer> {
    List<Level2> findAllByDirectoryName(String name);
}
