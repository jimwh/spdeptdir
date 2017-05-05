package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Level3Service {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private Level3Repository level3Repository;

    @Transactional
    public List<Level3> findAll() { return level3Repository.findAll(); }

    @Transactional
    public Level3 findById(Integer id) {
        return level3Repository.findOne(id);
    }

    @Transactional
    public List<Level3> findByDirectoryName(String name) {
        return level3Repository.findByDirectoryName(name);
    }

    public boolean hasJdbcTemplate() {
        return jdbcTemplate!=null;
    }
}
