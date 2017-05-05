package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level1;
import edu.columbia.cuitei.deptdir.domain.Level2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Level2Service {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private Level2Repository level2Repository;

    @Transactional
    public List<Level2> findAll() {
        return level2Repository.findAll();
    }

    @Transactional
    public Level2 findById(Integer id) {
        return level2Repository.findOne(id);
    }

    @Transactional
    public List<Level2> findByDirectoryName(String name) {
        return level2Repository.findByDirectoryName(name);
    }

    public boolean hasJdbcTemplate() {
        return jdbcTemplate!=null;
    }
}
