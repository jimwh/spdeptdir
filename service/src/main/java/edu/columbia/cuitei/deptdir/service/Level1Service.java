package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Directory;
import edu.columbia.cuitei.deptdir.domain.Level1;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Level1Service {

    private static final Logger log = LoggerFactory.getLogger(Level1Service.class);
    @Resource(name="level1Repository")
    private Level1Repository level1Repository;

    @Transactional
    List<Level1> getListByListId(List<Integer> list) {
        return level1Repository.getListByIdList(list);
    }

    @Transactional
    List<Level1> findByNameLike(String name) {
        return level1Repository.findByNameLikeOrderByName(name);
    }

    @Transactional
    public Level1 save(Level1 level1) {
        return level1Repository.save(level1);
    }

    @Transactional
    public Level1 findOne(Integer id) {
        return level1Repository.findOne(id);
    }

    @Transactional
    public List<Directory> findAll() {
        final List<Level1>list=level1Repository.findAll();
        final List<Directory> ls = new ArrayList<>();
        ls.addAll(list);
        return ls;
    }

    public Level1 update(Directory directory) {
        final Level1 level1 = findOne(directory.getId());
        level1.setName(directory.getName());
        level1.setAddress(directory.getAddress());
        level1.setMailCode(directory.getMailCode());
        level1.setPhoneType(directory.getPhoneType());
        level1.setPhoneNumber(directory.getPhoneNumber());
        level1.setTieLine(directory.getTieLine());
        return level1Repository.save(level1);
    }
}
