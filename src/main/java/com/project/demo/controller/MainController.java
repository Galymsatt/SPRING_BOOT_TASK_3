package com.project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {



    @GetMapping(path = "/")
    public String index(ModelMap model){
        return "index";
    }



}
