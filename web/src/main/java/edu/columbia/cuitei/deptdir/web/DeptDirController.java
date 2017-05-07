package edu.columbia.cuitei.deptdir.web;

import edu.columbia.cuitei.deptdir.service.QueryService;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptDirController {

    private static final Logger log = LoggerFactory.getLogger(DeptDirController.class);
    @Resource private QueryService queryService;

    @GetMapping("/deptdir")
    public Map<String, String> getDeptDir() {
        log.info("dude ...");
        return queryService.getDude();
    }
}