package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Directory;
import edu.columbia.cuitei.deptdir.domain.Level4;
import java.util.List;
import javax.annotation.Resource;

import edu.columbia.cuitei.deptdir.domain.Level4Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Level4Service {

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

    @Transactional
    public Level4 findOne(Integer id) {
        return level4Repository.findOne(id);
    }

    @Transactional
    public Level4 update(final Directory directory) {
        final Level4 level4 = findOne(directory.getId());
        level4.setName(directory.getName());
        level4.setAddress(directory.getAddress());
        level4.setMailCode(directory.getMailCode());
        level4.setPhoneType(directory.getPhoneType());
        level4.setPhoneNumber(directory.getPhoneNumber());
        level4.setTieLine(directory.getTieLine());
        return level4Repository.save(level4);
    }

    @Transactional
    public Level4 save(final Directory directory) {
        final Level4 level4 = new Level4();
        level4.setName(directory.getName());
        level4.setAddress(directory.getAddress());
        level4.setMailCode(directory.getMailCode());
        level4.setPhoneType(directory.getPhoneType());
        level4.setPhoneNumber(directory.getPhoneNumber());
        level4.setTieLine(directory.getTieLine());
        return level4Repository.save(level4);
    }

    @Transactional
    public void delete(Directory d) {
        level4Repository.delete(d.getId());
    }

    public List<Level4>findAllByParent(Integer id) {
        return level4Repository.findAllByParent(id);
    }

}
