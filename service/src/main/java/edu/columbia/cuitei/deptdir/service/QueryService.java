package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.DeptDirectory;
import edu.columbia.cuitei.deptdir.domain.Level1;
import edu.columbia.cuitei.deptdir.domain.Level2;
import edu.columbia.cuitei.deptdir.domain.Level3;
import edu.columbia.cuitei.deptdir.domain.Level4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QueryService {

    private static final Logger log = LoggerFactory.getLogger(QueryService.class);

    @Resource private Level1Service level1Service;
    @Resource private Level2Service level2Service;
    @Resource private Level3Service level3Service;
    @Resource private Level4Service level4Service;

    public List<DeptDirectory> search(String searchTerm) {

        List<Level2> level2List=level2Service.findByDirectoryNameLike(searchTerm);
        if( !level2List.isEmpty() ) {
            log.info("level2 directory_name like {}, level2List.size={}", searchTerm, level2List.size());
        }
        List<Integer>level2Parent=new ArrayList<>();
        List<Integer>level2IdList=new ArrayList<>();
        Map<Integer, List<DeptDirectory>> level1IdLevel2Map = new HashMap<>();
        for(Level2 level2: level2List) {
            Integer parent=level2.getParent();
            level2Parent.add(level2.getParent());
            level2IdList.add(level2.getId());
            List<DeptDirectory> temp = level1IdLevel2Map.get(parent);
            if( temp == null ) {
                temp = new ArrayList<>();
                temp.add(level2);
            }else {
                temp.add(level2);
            }
            level1IdLevel2Map.put(parent, temp);
        }

        List<Level1> level1List=level1Service.findAll(level2Parent);
        if( !level1List.isEmpty() ) {
            log.info("level1.size={} from level2Parent.size={}", level1List.size(),level2Parent.size());
        }

        List<Level3> level3List = level3Service.findAllByParentIn(level2IdList);
        List<Integer> level3IdList = new ArrayList<>();
        Map<Integer, List<DeptDirectory>> level2IdLevel3Map = new HashMap<>();
        if( !level3List.isEmpty() ) {
            log.info("level3.size={} from level2IdList.size={}", level3List.size(),level2IdList.size());
            for(Level3 level3: level3List) {
                Integer parent = level3.getParent();
                List<DeptDirectory> list = level2IdLevel3Map.get(parent);
                if( list== null ) {
                    list = new ArrayList<>();
                }
                list.add(level3);
                level2IdLevel3Map.put(parent, list);
                level3IdList.add(level3.getId());
            }
        }
        log.info("level3IdList.size={}, id={}", level3IdList.size(), level3IdList.toArray());

        List<Level4> level4List = level4Service.findAllByParentIn(level3IdList);
        Map<Integer, List<DeptDirectory>> level3IdLevel4Map = new HashMap<>();
        List<Integer> level4IdList = new ArrayList<>();
        if( !level4List.isEmpty() ) {
            log.info("level4.size={}", level4List.size());
            for(Level4 level4: level4List) {
                level4IdList.add(level4.getId());
                Integer parent = level4.getParent();
                List<DeptDirectory> list = level3IdLevel4Map.get(parent);
                if( list== null ) {
                    list = new ArrayList<>();
                }
                list.add(level4);
                level3IdLevel4Map.put(parent, list);
            }
        }

        //
        List<DeptDirectory> deptDirectoryList = new ArrayList<>();
        for(Level1 level1: level1List) {
            deptDirectoryList.add(level1);
            List<DeptDirectory> level2Data=level1IdLevel2Map.get(level1.getId());
            if( level2Data != null ) {
                for(DeptDirectory level2: level2Data) {
                    List<DeptDirectory> level3ListData = level2IdLevel3Map.get(level2.getId());
                    deptDirectoryList.add(level2);
                    if( level3ListData != null ) {
                        for(DeptDirectory level3: level3ListData) {
                            deptDirectoryList.add(level3);
                            List<DeptDirectory> level4ListData = level3IdLevel4Map.get(level3.getId());
                            if( level4ListData != null ) {
                                deptDirectoryList.addAll(level4ListData);
                            }
                        }
                    }
                }
            }
        }

        printData(deptDirectoryList);
        log.info("rows={}", deptDirectoryList.size());
        return deptDirectoryList;
    }

    private void printData(List<DeptDirectory> list) {
        for(DeptDirectory d: list) {
            log.info("{}", d.toString());
        }
    }
}
