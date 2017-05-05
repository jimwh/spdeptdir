package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Level4Service {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private Level4Repository level4Repository;

    @Transactional
    public List<Level4> findAll() {
        return level4Repository.findAll();
    }

    @Transactional
    public Level4 findById(Integer id) {
        return level4Repository.findOne(id);
    }

    @Transactional
    public List<Level4> findByDirectoryName(String name) {
        return level4Repository.findByDirectoryName(name);
    }

    public boolean hasJdbcTemplate() {
        return jdbcTemplate!=null;
    }
}
