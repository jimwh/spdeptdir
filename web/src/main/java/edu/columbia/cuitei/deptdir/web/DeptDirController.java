package edu.columbia.cuitei.deptdir.web;

import edu.columbia.cuitei.deptdir.domain.Directory;
import edu.columbia.cuitei.deptdir.domain.Level1;
import edu.columbia.cuitei.deptdir.service.Level1Service;
import edu.columbia.cuitei.deptdir.service.Level2Service;
import edu.columbia.cuitei.deptdir.service.Level3Service;
import edu.columbia.cuitei.deptdir.service.Level4Service;
import edu.columbia.cuitei.deptdir.service.QueryService;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestController
@Controller
public class DeptDirController {

    private static final Logger log = LoggerFactory.getLogger(DeptDirController.class);
    @Resource private QueryService queryService;
    @Resource private Level1Service level1Service;
    @Resource private Level2Service level2Service;
    @Resource private Level3Service level3Service;
    @Resource private Level4Service level4Service;

    @ModelAttribute(value = "directory")
    public Directory construct() {
        return new Directory();
    }

    @GetMapping(value = "/api/deptdir/search/{term}")
    @ResponseBody
    public List<Directory> getDeptDir(@PathVariable("term") String term) {
        log.info("term = {}", term);
        return queryService.search("%"+term+"%");
    }

    @PostMapping("/api/deptdir/addLevel1")
    public ResponseEntity<?> add(@RequestBody Level1 level1) {
        //validate level1 here
        log.info("directoryName={}", level1.getName());
        log.info("address={}", level1.getAddress());
        log.info("phoneNumber={}", level1.getPhoneNumber());
        level1Service.save(level1);
        return new ResponseEntity("level1 added", new HttpHeaders(), HttpStatus.OK);
    }


//////////////////////////////////////////////////////////////////////////////////////////
    @ResponseBody
    @GetMapping(value = "/amend/loadEntity/{id}")
    public Directory loadEntity(@PathVariable("id") Integer id) {
        log.info("loadEntity/id = {}", id);
        Directory d = level1Service.findOne(id);
        if( d == null ) {
            d = level2Service.findOne(id);
            if (d == null) {
                d = level3Service.findOne(id);
                if (d == null) {
                    d = level4Service.findOne(id);
                }
            }
        }
        return d;
    }


    @RequestMapping(value = "amend/update", method = RequestMethod.POST)
    @ResponseBody public Directory update(@ModelAttribute("directory") Directory directory) {
        log.info("hit update id={}, name={}, level={}", directory.getId(), directory.getName(), directory.getLevel());
        return queryService.update(directory);
        // queryService.update(directory);
    }

    /*
    @ResponseBody
    @RequestMapping(value = "/amend/loadDeptDirectory")
    public List<Directory> loadSomethingTable() {
        log.info("load table.................../amend/loadDeptDirectory");
        return level1Service.findAll();
    }
    */
    @ResponseBody
    @RequestMapping(value = "/amend/loadDirectory/{name}")
    public List<Directory> loadDirectory(@PathVariable("name") String name) {
        if(name == null) {
            name="arts";
            log.info("reset to arts");
        }
        log.info("load table.................../amend/loadDeptDirectory...{}", name);
        return queryService.search("%"+name+"%");
    }

    @GetMapping("amend/list")
    public String list(@RequestParam(value="name", defaultValue="arts") String name, Model model) {
        log.info("hit list with name={}", name);
        model.addAttribute("directories", queryService.search("%"+name+"%"));
        return "amend/list";
    }

    /*
    @RequestMapping(value = "something/create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("something/create");
    }

    @RequestMapping(value = "something/create", method = RequestMethod.POST)
    public void create(@ModelAttribute(value = "something") Something something) {
        somethingService.save(something);
    }
    */


    @ResponseBody
    @RequestMapping(value = "amend/delete/{id}", method = RequestMethod.POST)
    public void delete(@PathVariable("id") Integer id) {
        log.info("hit delete .................");
        // somethingService.delete(id);
    }

    private void print(Directory d) {
        log.info("id={},name={},parent={},level={}",d.getId(),d.getName(),d.getParent(),d.getLevel());
    }
}