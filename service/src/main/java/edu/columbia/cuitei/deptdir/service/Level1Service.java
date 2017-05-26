package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.DeptDirectory;
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
    List<Level1> findByDirectoryNameLike(String name) {
        return level1Repository.findByDirectoryNameLikeOrderByDirectoryName(name);
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
    public List<DeptDirectory> findAll() {
        final List<Level1>list=level1Repository.findAll();
        final List<DeptDirectory> ls = new ArrayList<>();
        ls.addAll(list);
        return ls;
    }

    public Level1 update(DeptDirectory deptDirectory) {
        final Level1 level1 = findOne(deptDirectory.getId());
        level1.setDirectoryName(deptDirectory.getDirectoryName());
        level1.setAddress(deptDirectory.getAddress());
        level1.setMailCode(deptDirectory.getMailCode());
        level1.setPhoneType(deptDirectory.getPhoneType());
        level1.setPhoneNumber(deptDirectory.getPhoneNumber());
        level1.setTieLine(deptDirectory.getTieLine());
        return level1Repository.save(level1);
    }
}
