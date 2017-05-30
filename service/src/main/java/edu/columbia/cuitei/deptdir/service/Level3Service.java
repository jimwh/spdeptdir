package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Directory;
import edu.columbia.cuitei.deptdir.domain.Level3;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Level3Service {

    @Resource
    private Level3Repository level3Repository;

    @Transactional
    public List<Level3> findAll() { return level3Repository.findAll(); }

    @Transactional
    public Level3 findById(Integer id) {
        return level3Repository.findOne(id);
    }

    @Transactional
    List<Level3> findAllByParentIn(List<Integer> list) {
        return level3Repository.findAllByParentIn(list);
    }


    @Transactional
    public Level3 findOne(Integer id) {
        return level3Repository.findOne(id);
    }

    @Transactional
    public Level3 update(Directory directory) {
        final Level3 level3 = findOne(directory.getId());
        level3.setDirectoryName(directory.getDirectoryName());
        level3.setAddress(directory.getAddress());
        level3.setMailCode(directory.getMailCode());
        level3.setPhoneType(directory.getPhoneType());
        level3.setPhoneNumber(directory.getPhoneNumber());
        level3.setTieLine(directory.getTieLine());
        return level3Repository.save(level3);
    }

}
