package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Level1Service {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource(name="level1Repository")
    private Level1Repository level1Repository;

    @Transactional
    public List<Level1> findAll() {
        return level1Repository.findAll();
    }

    @Transactional
    public Level1 findById(Integer id) {
        return level1Repository.findOne(id);
    }

    @Transactional
    public Level1 findByDirectoryName(String name) {
        return level1Repository.findByDirectoryName(name);
    }

    public boolean hasJdbcTemplate() {
        return jdbcTemplate!=null;
    }
}
