package edu.columbia.cuitei.deptdir.web;

import edu.columbia.cuitei.deptdir.service.QueryService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    @Resource
    private QueryService queryService;

    @GetMapping("/index.html")
    public String index() {
        log.info("hit index ...");
        return "index";
    }

}