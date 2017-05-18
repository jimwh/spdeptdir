package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.DeptDirectory;
import edu.columbia.cuitei.deptdir.domain.Level2;

import java.util.ArrayList;
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
        return level2Repository.findByDirectoryNameLikeOrderByDirectoryName(name);
    }

    @Transactional
    List<DeptDirectory> getByDirectoryNameLike(String name) {
        List<DeptDirectory> deptDirectoryList=new ArrayList<>();
        List<Level2> list =  level2Repository.findByDirectoryNameLikeOrderByDirectoryName(name);
        for(Level2 level2: list) {
            deptDirectoryList.add(level2);
        }
        return deptDirectoryList;
    }


    @Transactional
    List<Level2> findAllByParent(List<Integer> parentList) {
        return level2Repository.findAllByParentInOrderByDirectoryName(parentList);
    }

}
