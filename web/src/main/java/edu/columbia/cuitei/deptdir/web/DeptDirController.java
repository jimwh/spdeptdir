package edu.columbia.cuitei.deptdir.web;

import edu.columbia.cuitei.deptdir.domain.Level1;
import edu.columbia.cuitei.deptdir.service.Level1Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DeptDirController {

    private static final Logger log = LoggerFactory.getLogger(DeptDirController.class);
    @Resource private Level1Service level1Service;

    @GetMapping("/deptdir")
    public Map<String, String> getDeptDir() {
        log.info("dude ...");
        List<Level1> level1List = level1Service.findAll();
        if( !level1List.isEmpty() ) {
            for (Level1 level1 : level1List) {
                log.info("directoryName={}", level1.getDirectoryName());
            }
            log.info("size={}", level1List.size());
        }
        //
        Level1 one = level1Service.findById(273606);
        if(one != null) {
            log.info("findById: id={}", one.getId());
        }
        //
        List<Level1> oneList = level1Service.findByDirectoryName("foome1");
        if( !oneList.isEmpty() ) {
            log.info("findByDirectoryName: name={}", oneList.get(0).getDirectoryName());
        }

        Map<String,String> map = new HashMap<String,String>();
        map.put("foo", "bar");
        map.put("fred", "gary");

        log.info("hasJdbcTemplate={}", level1Service.hasJdbcTemplate());
        return map;
    }
}