package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level1;
import edu.columbia.cuitei.deptdir.domain.Level2;
import edu.columbia.cuitei.deptdir.domain.Level3;
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

    public Map<String,String>getDude() {
        String searchTerm="%arts%";
        search(searchTerm);

        Map<String,String> map = new HashMap<>();
        map.put("foo", "bar");
        map.put("fred", "gary");

        log.info("hasJdbcTemplate={}", level1Service.hasJdbcTemplate());
        return map;
    }

    private Map<String,String>search(String searchTerm) {

        List<Level2> level2List=level2Service.findByDirectoryNameLike(searchTerm);
        if( !level2List.isEmpty() ) {
            log.info("level2 directory_name like {}, level2List.size={}", searchTerm, level2List.size());
        }
        List<Integer>level2Parent=new ArrayList<>();
        List<Integer>level2IdList=new ArrayList<>();

        for(Level2 level2: level2List) {
            level2Parent.add(level2.getParent());
            level2IdList.add(level2.getId());
        }
        List<Level1> list=level1Service.findAll(level2Parent);
        if( !list.isEmpty() ) {
            log.info("level1.size={} from level2Parent.size={}", list.size(),level2Parent.size());
        }

        List<Level3> level3List = level3Service.findAllByParent(level2IdList);
        if( !level3List.isEmpty() ) {
            log.info("level3.size={} from level2IdList.size={}", level3List.size(),level2IdList.size());
        }
        return new HashMap<>();
    }

}
