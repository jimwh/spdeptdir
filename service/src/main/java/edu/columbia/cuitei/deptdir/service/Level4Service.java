package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level4;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class Level4Service {

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
    List<Level4> findAllByParentIn(List<Integer> list) {
        return level4Repository.findAllByParentIn(list);
    }

}
