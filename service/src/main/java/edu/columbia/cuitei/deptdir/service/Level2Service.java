package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Directory;
import edu.columbia.cuitei.deptdir.domain.Level2;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Level2Service {

    @Resource
    private Level2Repository level2Repository;

    @Transactional
    List<Level2> findByNameLike(String name) {
        return level2Repository.findByNameLikeOrderByName(name);
    }

    @Transactional
    List<Directory> getByNameLike(String name) {
        List<Directory> directoryList =new ArrayList<>();
        List<Level2> list =  level2Repository.findByNameLikeOrderByName(name);
        for(Level2 level2: list) {
            directoryList.add(level2);
        }
        return directoryList;
    }


    @Transactional
    List<Level2> findAllByParent(List<Integer> parentList) {
        return level2Repository.findAllByParentInOrderByName(parentList);
    }

    @Transactional
    public Level2 findOne(Integer id) {
        return level2Repository.findOne(id);
    }


    @Transactional
    public List<Directory> findAll() {
        final List<Directory> list=new ArrayList<>();
        final List<Level2> ls = level2Repository.findAll();
        list.addAll(ls);
        return list;
    }

    @Transactional
    public Level2 update(Directory directory) {
        final Level2 level2 = findOne(directory.getId());
        level2.setName(directory.getName());
        level2.setAddress(directory.getAddress());
        level2.setMailCode(directory.getMailCode());
        level2.setPhoneType(directory.getPhoneType());
        level2.setPhoneNumber(directory.getPhoneNumber());
        level2.setTieLine(directory.getTieLine());
        return level2Repository.save(level2);
    }

    @Transactional
    public Level2 save(final Directory directory) {
        final Level2 level2 = new Level2();
        level2.setName(directory.getName());
        level2.setAddress(directory.getAddress());
        level2.setMailCode(directory.getMailCode());
        level2.setPhoneType(directory.getPhoneType());
        level2.setPhoneNumber(directory.getPhoneNumber());
        level2.setTieLine(directory.getTieLine());
        return level2Repository.save(level2);
    }

    @Transactional
    public void delete(Directory d) {
        level2Repository.delete(d.getId());
    }

    public List<Level2>findAllByParent(Integer id) {
        return level2Repository.findAllByParent(id);
    }
}
