package edu.columbia.cuitei.deptdir.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Level4Repository extends CrudRepository<Level1, Integer> {
    List<Level4> findAllByDirectoryName(String name);
}
