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
import java.util.Set;
import java.util.TreeSet;
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
        final List<DeptDirectory> deptDirectoryList = new ArrayList<>();


        // 1. find from level1 first
        final Set<Integer> level2ParentIdSet=getTopLevelIdSetByName(searchTerm);
        log.info("level1ParentId.size={}", level2ParentIdSet.size());


        // 2. find all level2 by directory name
        final Set<Integer>level2IdListSet=new TreeSet<>();
        final Map<Integer, List<DeptDirectory>> level1IdLevel2Map = new HashMap<>();
        // final List<Level2> level2List=level2Service.findByDirectoryNameLike(searchTerm);
        // using name like from level2 vs using parent id from level2 // that will have different level2 size
        final List<DeptDirectory> level2List=level2Service.getByDirectoryNameLike(searchTerm);
        if( level2List.isEmpty() ) {
            return deptDirectoryList;
        }
        /*
        for(Level2 level2: level2List) {
            Integer parent=level2.getParent();
            level2ParentIdSet.add(level2.getParent());
            level2IdListSet.add(level2.getId());
            List<DeptDirectory> temp = level1IdLevel2Map.get(parent);
            if( temp == null ) {
                temp = new ArrayList<>();
                temp.add(level2);
            }else {
                temp.add(level2);
            }
            level1IdLevel2Map.put(parent, temp);
        }
        */

        //fill(List<DeptDirectory>list, Set<Integer>parentIdSet, Set<Integer>idListSet, Map<Integer, List<DeptDirectory>>map) {
        fill(level2List, level2ParentIdSet, level2IdListSet, level1IdLevel2Map);
        /*
        final List<Level2> level2ListByParent = level2Service.findAllByParent(new ArrayList<>(level2ParentIdSet));
        for(Level2 level2: level2ListByParent) {
            log.info("level2: {}, {}", level2.getDirectoryName(), level2.getParent());
        }
        level2List.clear();
        level2List.addAll(level2ListByParent);
        */
        //

        // 3. find all level1 by id column that is level2's parent column
        // List<Level1> level1List=level1Service.findAll(level2Parent);
        final List<Level1> level1List=level1Service.getListByListId(new ArrayList<Integer>(level2ParentIdSet));
        if( !level1List.isEmpty() ) {
            log.info("level1.size={} from level2Parent.size={}", level1List.size(),level2ParentIdSet.size());
        }

        // 4. find all level3 by level3's parent which is level2 id
        final List<Level3> level3List = level3Service.findAllByParentIn(new ArrayList<Integer>(level2IdListSet));
        final List<Integer> level3IdList = new ArrayList<>();
        final Map<Integer, List<DeptDirectory>> level2IdLevel3Map = new HashMap<>();

        // private void fill(List<DeptDirectory>list, Set<Integer>parentIdSet, Set<Integer>idSet, Map<Integer, List<DeptDirectory>>map) {
        // fill(level3List, null, level3IdList, level2IdLevel3Map);

        if( !level3List.isEmpty() ) {
            log.info("level3.size={} from level2IdList.size={}", level3List.size(),level2IdListSet.size());
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

        // 5. find all level4 by level4 parent column which is level3 id
        log.info("level3IdList.size={}, id={}", level3IdList.size(), level3IdList.toArray());
        final List<Level4> level4List = level4Service.findAllByParentIn(level3IdList);
        final Map<Integer, List<DeptDirectory>> level3IdLevel4Map = new HashMap<>();
        final List<Integer> level4IdList = new ArrayList<>();
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

        // 6. create a dept dir list
        for(Level1 level1: level1List) {
            deptDirectoryList.add(level1);
            final List<DeptDirectory> level2Data=level1IdLevel2Map.get(level1.getId());
            if( level2Data != null ) {
                for(DeptDirectory level2: level2Data) {
                    deptDirectoryList.add(level2);

                    final List<DeptDirectory> level3ListData = level2IdLevel3Map.get(level2.getId());
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

    private void fill(List<DeptDirectory>list, Set<Integer>parentIdSet, Set<Integer>idSet, Map<Integer, List<DeptDirectory>>map) {
        for (DeptDirectory deptDirectory : list) {
            final Integer parent = deptDirectory.getParent();
            parentIdSet.add(parent);
            idSet.add(deptDirectory.getId());
            List<DeptDirectory> temp = map.get(parent);
            if (temp == null) {
                temp = new ArrayList<>();
                temp.add(deptDirectory);
            } else {
                temp.add(deptDirectory);
            }
            map.put(parent, temp);
        }
    }

    private Set<Integer>getTopLevelIdSetByName(String searchTerm) {
        final List<Level1> topLikeList = level1Service.findByDirectoryNameLike(searchTerm);
        final Set<Integer> level2ParentIdSet = new TreeSet<>();
        for (Level1 level1 : topLikeList) {
            level2ParentIdSet.add(level1.getId());
        }
        log.info("level1ParentId.size={}", level2ParentIdSet.size());
        return level2ParentIdSet;
    }

}
