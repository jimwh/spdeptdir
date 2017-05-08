package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level1;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class Level1Service {

    @Resource(name="level1Repository")
    private Level1Repository level1Repository;

    @Transactional
    public List<Level1> findAll() {
        return level1Repository.findAll();
    }

    @Transactional
    List<Level1> findAll(List<Integer> list) {
        return level1Repository.findAll(list);
    }

    @Transactional
    public Level1 findById(Integer id) { return level1Repository.findOne(id); }

    @Transactional
    public List<Level1> findByDirectoryName(String name) {
        return level1Repository.findByDirectoryName(name);
    }

}
