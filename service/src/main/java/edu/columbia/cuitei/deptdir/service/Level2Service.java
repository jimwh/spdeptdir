package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level2;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class Level2Service {

    @Resource
    private Level2Repository level2Repository;

    @Transactional
    List<Level2> findByDirectoryNameLike(String name) {
        return level2Repository.findByDirectoryNameLike(name);
    }

}
