package edu.columbia.cuitei.deptdir.web;

import edu.columbia.cuitei.deptdir.domain.DeptDirectory;
import edu.columbia.cuitei.deptdir.domain.Level1;
import edu.columbia.cuitei.deptdir.service.Level1Service;
import edu.columbia.cuitei.deptdir.service.QueryService;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptDirController {

    private static final Logger log = LoggerFactory.getLogger(DeptDirController.class);
    @Resource private QueryService queryService;
    @Resource private Level1Service level1Service;

    @GetMapping(value = "/api/deptdir/search/{term}")
    @ResponseBody
    public List<DeptDirectory> getDeptDir(@PathVariable("term") String term) {
        log.info("term = {}", term);
        return queryService.search("%"+term+"%");
    }

    @PostMapping("/api/deptdir/addLevel1")
    public ResponseEntity<?> add(@RequestBody Level1 level1) {

        //validate level1 here
        log.info("directoryName={}", level1.getDirectoryName());
        log.info("address={}", level1.getAddress());
        log.info("phoneNumber={}", level1.getPhoneNumber());
        level1Service.save(level1);
        return new ResponseEntity("level1 added", new HttpHeaders(), HttpStatus.OK);
    }

}