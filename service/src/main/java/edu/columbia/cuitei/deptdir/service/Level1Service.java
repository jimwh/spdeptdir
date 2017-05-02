package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level1;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Level1Service {

    @Resource(name="level1Repository")
    private Level1Repository level1Repository;

    public List<Level1> findAll() {
        return level1Repository.findAll();
    }
}
