package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.DeptDirectory;
import edu.columbia.cuitei.deptdir.domain.Level2;
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
    public Level3 update(DeptDirectory deptDirectory) {
        final Level3 level3 = findOne(deptDirectory.getId());
        level3.setDirectoryName(deptDirectory.getDirectoryName());
        level3.setAddress(deptDirectory.getAddress());
        level3.setMailCode(deptDirectory.getMailCode());
        level3.setPhoneType(deptDirectory.getPhoneType());
        level3.setPhoneNumber(deptDirectory.getPhoneNumber());
        level3.setTieLine(deptDirectory.getTieLine());
        return level3Repository.save(level3);
    }

}
