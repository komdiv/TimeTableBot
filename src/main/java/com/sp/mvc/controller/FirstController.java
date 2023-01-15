package com.sp.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name", required = false) String name){
        System.out.println("Привет " + name);
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage(){
        return "first/goodbye";
    }

    @GetMapping("/work")
    public String work(@RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "do", required = false) String doWork,
                       Model model){
        //model.addAttribute("message","" + name + "" + doWork);
        model.addAttribute("message", "" + name + ", it's time for " + doWork);
        System.out.println("норм");
        return "first/work";
    }

}
