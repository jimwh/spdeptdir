package edu.columbia.cuitei.deptdir.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index0() {
        return "index";
    }

    @GetMapping("/index.html")
    public String index() {
        return "index";
    }

}