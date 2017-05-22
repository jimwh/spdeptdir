package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level1;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Level1Service {

    @Resource(name="level1Repository")
    private Level1Repository level1Repository;

    @Transactional
    List<Level1> getListByListId(List<Integer> list) {
        return level1Repository.getListByIdList(list);
    }

    @Transactional
    List<Level1> findByDirectoryNameLike(String name) {
        return level1Repository.findByDirectoryNameLikeOrderByDirectoryName(name);
    }

    @Transactional
    public Level1 save(Level1 level1) {
        return level1Repository.save(level1);
    }
}
