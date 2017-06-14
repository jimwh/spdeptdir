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

    @ResponseBody
    @GetMapping(value = "/amend/addtop")
    public Directory addTop() {
        log.info("add top level ...");
        final Directory d = new Level1();
        return d;
    }

    @PostMapping(value = "/amend/addtop")
    public String addTop(@Valid final Directory directory, final BindingResult bindingResult) {
        log.info("add top level name={}", directory.getName());
        return "redirect:/amend/list";
    }

    @RequestMapping(value = "amend/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("directory") Directory directory) {
        log.info("hit add id={}, parent={}, name={}, level={}", directory.getId(), directory.getParent(), directory.getName(), directory.getLevel());
        queryService.create(directory);
        return "redirect:/amend/list?name=arts";
    }

    @RequestMapping(value = "amend/update", method = RequestMethod.POST)
    @ResponseBody public Directory update(@ModelAttribute("directory") Directory directory) {
        log.info("hit update id={}, name={}, level={}", directory.getId(), directory.getName(), directory.getLevel());
        return queryService.update(directory);
    }

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

    /*
    @GetMapping("amend/list")
    public String list(@RequestParam(value="searchTerm", defaultValue="arts") String searchTerm, Model model) {
        log.info("hit list with name={}", searchTerm);
        model.addAttribute("directories", queryService.search("%"+searchTerm+"%"));
        return "amend/list";
    }
    */
    @GetMapping("/amend/list")
    public String getList(Model model) {
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


    @ResponseBody
    @RequestMapping(value = "amend/directory/delete/{id}", method = RequestMethod.POST)
    public void delete(@PathVariable("id") final Integer id) {
        log.info("delete id = {}", id);
        queryService.delete(id);
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

    private void formatName(List<Directory> list) {
        for (Directory d : list) {
            if( "LEVEL1".equals(d.getLevel())) {
                formatLevel1Name(d);
            }
        }
    }

    private void formatLevel1Name(Directory d) {
        String address = d.getAddress();
        if( !StringUtils.isBlank(address) ) {
            //address = "</br>Address:&nbsp;&nbsp;" + address;
            address = "\n  " + address;
        }
        String mailCode = d.getMailCode();
        if( !StringUtils.isBlank(mailCode)) {
            //mailCode = "&nbsp;&nbsp;Mail Code:&nbsp;&nbsp;" + mailCode;
            mailCode = " Mail Code:  " + mailCode;
        }

        String phone = d.getPhoneNumber();
        if( !StringUtils.isBlank(phone)) {
            //phone = "&nbsp;&nbsp;Phone: &nbsp;&nbsp;"+ phone + "&nbsp;&nbsp;";
            phone = "  Phone:  "+ phone + "  ";
        }

        String tieLine  = d.getTieLine();
        if( !StringUtils.isBlank(tieLine)) {
            //tieLine = "Tie Line: &nbsp;&nbsp;"+ tieLine;
            tieLine = "Tie Line:   "+ tieLine;
        }

        //String name = d.getName() + address + mailCode + phone + "&nbsp;&nbsp;" + tieLine;
        String name = d.getName() + "<br>" + address + mailCode + phone + "  " + tieLine;
        d.setName(name);

    }


    /*
    function getLevel1(data) {
    var bg = '<tr class="active"><td>';
    var row = "<strong>" + data.name + "</strong>";
    var address = "";
    if (data.address != "") {
        address = "</br>Address:&nbsp;&nbsp;" + data.address;
    }
    var mailCode = "";
    if (data.mailCode != "") {
        if(address!="") {
            mailCode = "&nbsp;&nbsp;Mail Code:&nbsp;&nbsp;" + data.mailCode;
        }else {
            mailCode = "<br/>MAILCODE:&nbsp;&nbsp;" + data.mailCode;
        }
    }else {
        if(address!="") {
            mailCode = "&nbsp;&nbsp;";
        }else {
            mailCode = "<br/>";
        }
    }
    var phone="";
    if( data.phoneNumber != "") {
        phone = "&nbsp;&nbsp;Phone: &nbsp;&nbsp;"+data.phoneNumber + '&nbsp;&nbsp;';
    }
    var tieLine="";
    if( data.tieLine != "") {
        tieLine = "Tie Line: &nbsp;&nbsp;"+data.tieLine;
    }

    row = bg + row + address + mailCode + phone + '&nbsp;&nbsp;' + tieLine + '</td></tr>';
    return row;
}

     */
}