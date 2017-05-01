package edu.columbia.cuitei.deptdir.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Level3Repository extends CrudRepository<Level1, Integer> {
    List<Level3> findAllByDirectoryName(String name);
}
