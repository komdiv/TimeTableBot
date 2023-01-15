package com.sp.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpringController {




    @GetMapping("/spring")
    public String springBeans(@RequestParam(value = "name", required = false) String name,
                              Model model){
        System.out.println("Это контроллер спринга");
        System.out.println("Hello " + name);
        model.addAttribute("name", "Hello " + name);
        return "spring/beans";
    }

}
