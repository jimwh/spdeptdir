package edu.columbia.cuitei.deptdir.controller;

import edu.columbia.cuitei.deptdir.domain.Directory;
import edu.columbia.cuitei.deptdir.domain.Level1;
import edu.columbia.cuitei.deptdir.service.Level1Service;
import edu.columbia.cuitei.deptdir.service.Level2Service;
import edu.columbia.cuitei.deptdir.service.Level3Service;
import edu.columbia.cuitei.deptdir.service.Level4Service;
import edu.columbia.cuitei.deptdir.service.QueryService;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeptDirController {

    private static final Logger log = LoggerFactory.getLogger(DeptDirController.class);
    private static final String Space2 = "&nbsp;&nbsp;";
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
    public List<Directory> getDeptDir(@PathVariable("term") final String term) {
        log.info("term = {}", term);
        return queryService.search("%"+term+"%");
    }

    @PostMapping("/api/deptdir/addLevel1")
    public ResponseEntity<?> add(@RequestBody Level1 level1) {
        //validate level1 here
        level1Service.save(level1);
        return new ResponseEntity("level1 added", new HttpHeaders(), HttpStatus.OK);
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    @ResponseBody
    @GetMapping(value = "/amend/loadEntity/{id}")
    public Directory loadEntity(@PathVariable("id") Integer id) {
        log.info("loadEntity/id = {}", id);
        return queryService.getDirectoryById(id);
    }

    @ResponseBody
    @GetMapping(value = "/amend/addtop")
    public Directory addTop() {
        log.info("add top level ...");
        final Directory d = new Level1();
        return d;
    }

    @PostMapping(value = "/amend/addtop")
    public String addTop(@Valid final Directory directory, final BindingResult bindingResult) throws Exception {
        log.info("add top level name={}", directory.getName());
        if( StringUtils.isBlank(directory.getName())) {
            throw new Exception("The directory name cannot be empty.");
        }
        level1Service.save(directory);
        return "redirect:/amend/list";
    }


    @PostMapping(value = "/amend/add")
    public String addChild(final Directory directory) throws Exception {
    //public String addChild(@ModelAttribute("directory") final Directory directory) throws Exception {
        log.info("post mapping addChild id={}, parent={}, name={}, level={}", directory.getId(), directory.getParent(), directory.getName(), directory.getLevel());
        if( StringUtils.isBlank(directory.getName()) || directory.getParent()==null) {
            throw new Exception("name & parent id cannot be empty.");
        }
        queryService.createChild(directory);
        return "redirect:/amend/list";
    }


    @PostMapping(value = "/amend/update")
    @ResponseBody public Directory update(@ModelAttribute("directory") Directory directory) {
        log.info("UPDATE: id={}, name={}, level={}", directory.getId(), directory.getName(), directory.getLevel());
        return queryService.update(directory);
    }

    @ResponseBody
    @RequestMapping(value = "/amend/loadDirectory/{name}")
    public List<Directory> loadDirectory(@PathVariable("name") String name) {
        if(name == null) {
            name="arts";
            log.info("reset to arts");
        }
        log.info("load table .../amend/loadDeptDirectory...{}", name);
        return queryService.search("%"+name+"%");
    }

    @GetMapping("/amend/list")
    public String getList(final Model model) {
        model.addAttribute("searchTerm", new String());
        return "amend/list";
    }

    @PostMapping("/amend/list")
    public String postList(@RequestParam(value="searchTerm") final String searchTerm, final Model model) {
        log.info("hit list with searchTerm={}", searchTerm);
        model.addAttribute("directories", queryService.search("%"+searchTerm+"%"));
        model.addAttribute("searchTerm", searchTerm);
        return "amend/list";
    }

    @GetMapping(value = "/amend/directory/delete/{id}")
    public String delete(@PathVariable("id") final Integer id) {
        log.info("DELETE directory by id = {}", id);
        queryService.delete(id);
        return "amend/list";
    }

    @GetMapping("/search")
    public String publicSearch(@RequestParam(value="term", required = false) final String term, final Model model) {
        log.info("hit search page with term={}", term);
        if(term==null || "".equals(term) ) {
            model.addAttribute("feedback", "");
        } else {
            final List<Directory> list = queryService.search("%" + term + "%");
            formatName(list);
            model.addAttribute("directories", list);
            model.addAttribute("searchTerm", term);
            if( list.isEmpty() ) {
                final String feedback = String.format("Your search for <strong>\"%s\"</strong> returned no results.", term);
                model.addAttribute("feedback", feedback);
            }else {
                final String feedback = String.format("Your search for <strong>\"%s\"</strong> returned %d results.", term, list.size());
                model.addAttribute("feedback", feedback);
            }
        }

        return "search";
    }

    @GetMapping("/search.html")
    public String searchHtml() {
        log.info("search.html...");
        return "search";
    }

    @GetMapping("/search/")
    public String trailingSlash() {
        log.info("trailing slash...");
        return "redirect:/search";
    }

    private void formatName(final List<Directory> list) {
        for (Directory d : list) {
            final String level = d.getLevel();
            if( "LEVEL1".equals(level) || "LEVEL2".equals(level)) {
                formatLevel1Name(d);
            }
            else if("LEVEL3".equals(d.getLevel())){
                formatLevel3Name(d);
            }
        }
    }

    private void formatLevel1Name(final Directory d) {
        String address = d.getAddress();
        if( !StringUtils.isBlank(address) ) {
            address = Space2 + address;
        }
        String mailCode = d.getMailCode();
        if( !StringUtils.isBlank(mailCode)) {
            mailCode = Space2 + "Mail Code: " + mailCode;
        }

        String phone = d.getPhoneNumber();
        if( !StringUtils.isBlank(phone)) {
            phone = Space2 + "Phone: "+ phone + "  ";
        }

        String tieLine  = d.getTieLine();
        if( !StringUtils.isBlank(tieLine)) {
            tieLine = Space2 + "Tie Line: "+ tieLine;
        }

        final String name = String.format("%s<br>%s%s%s  %s",d.getName(),address,mailCode,phone,tieLine);
        if("LEVEL2".equals(d.getLevel())) {
            d.setName(String.format("%s%s", Space2, name));
        }else {
            d.setName(name);
        }


    }

    private void formatLevel3Name(final Directory d) {
        String address = d.getAddress();
        if( !StringUtils.isBlank(address) ) {
            address = "\n  " + address;
        }
        String mailCode = d.getMailCode();
        if( !StringUtils.isBlank(mailCode)) {
            mailCode = Space2 + " Mail Code:  " + mailCode;
        }

        String phone = d.getPhoneNumber();
        if( !StringUtils.isBlank(phone)) {
            if( !"fax".equalsIgnoreCase(d.getName())) {
                phone = Space2 + "Phone:  " + phone;
            }else {
                d.setName("FAX:");
            }
        }

        String tieLine  = d.getTieLine();
        if( !StringUtils.isBlank(tieLine)) {
            tieLine = Space2 + "Tie Line:   "+ tieLine;
        }

        final String name = String.format("%s%s %s&nbsp;%s%s%s  %s",Space2,Space2,d.getName(),address,mailCode,phone,tieLine);
        d.setName(name);

    }

}