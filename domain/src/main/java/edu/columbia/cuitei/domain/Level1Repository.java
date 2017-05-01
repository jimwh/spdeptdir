package edu.columbia.cuitei.deptdir.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Level1Repository extends CrudRepository<Level1, Integer> {
    List<Level1> findAllByDirectoryName(String name);
}
