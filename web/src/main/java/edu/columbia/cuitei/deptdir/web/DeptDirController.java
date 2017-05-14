package edu.columbia.cuitei.deptdir.web;

import edu.columbia.cuitei.deptdir.domain.DeptDirectory;
import edu.columbia.cuitei.deptdir.service.QueryService;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptDirController {

    private static final Logger log = LoggerFactory.getLogger(DeptDirController.class);
    @Resource private QueryService queryService;

    @GetMapping(value = "/api/deptdir/search/{term}")
    @ResponseBody
    public List<DeptDirectory> getDeptDir(@PathVariable("term") String term) {
        log.info("term = {}", term);
        return queryService.search("%"+term+"%");
    }

}