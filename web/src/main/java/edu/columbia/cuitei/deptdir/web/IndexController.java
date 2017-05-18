package edu.columbia.cuitei.deptdir.web;

import edu.columbia.cuitei.deptdir.service.QueryService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    @Resource
    private QueryService queryService;

    @GetMapping("/")
    public String index() {
        log.info("hit index ...");
        return "index";
    }

    // http://localhost:8080/another?name=User.
    @GetMapping("/another")
    public String another(@RequestParam(value="name", required=false,
            defaultValue="World") String name, Model model) {
        log.info("hit another {} ...", name);
        model.addAttribute("name", name);

        model.addAttribute("prods", queryService.search("%"+name+"%"));

        return "another";
    }

}