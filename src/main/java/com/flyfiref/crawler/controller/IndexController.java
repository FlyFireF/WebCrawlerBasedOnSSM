package com.flyfiref.crawler.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class IndexController {
    @RequestMapping("/")
    public String doIndex(){
        return "index";
    }
}