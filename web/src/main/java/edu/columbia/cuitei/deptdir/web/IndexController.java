package edu.columbia.cuitei.deptdir.web;

import edu.columbia.cuitei.deptdir.domain.DeptDirectory;
import edu.columbia.cuitei.deptdir.service.QueryService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    /*
    @GetMapping("/another")
    public String another(@RequestParam(value="name", required=false,
            defaultValue="World") String name, Model model) {
        log.info("hit another {} ...", name);
        model.addAttribute("name", name);
        model.addAttribute("prods", queryService.search("%"+name+"%"));
        return "another";
    }
    */
    @GetMapping("/another")
    public String another(String name, Model model) {
        name = "arts";
        log.info("hit another {} ...", name);
        model.addAttribute("name", name);
        model.addAttribute("prods", queryService.search("%"+name+"%"));
        return "another";
    }

    @ResponseBody
    @RequestMapping(value = "another/update", method = RequestMethod.POST)
    public void update(@ModelAttribute("prod") DeptDirectory prod) {
        // somethingService.save(something);
    }

}