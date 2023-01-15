package com.sp.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

@Controller

public class ThirdController {

    @GetMapping("calc")
    public String math(@RequestParam(value = "x",required = false) String x,
                       @RequestParam(value = "y", required = false) String y,
                       @RequestParam(value = "math", required = false) String math, Model model){
        String answer="";
        try {
            Double X = Double.parseDouble(x);
            Double Y = Double.parseDouble(y);
            Double C;
            switch (math){
                case "plus":
                    System.out.println(X+Y + " - это ответ суммы");
                    answer=String.valueOf(X+Y);
                    break;
                case "minus":
                    answer=String.valueOf(X-Y);
                    break;
            }
            model.addAttribute("x",""+x);
            model.addAttribute("y",""+y);
                model.addAttribute("answer", ""+answer);
            System.out.println(x + "+" + y + "=" + answer);
        }
        catch (Exception e){
            answer = "unknown";
        }
        return "third/calc";
    }


    @GetMapping("lambda")
    public String lambda(@RequestParam(value = "f",required = false) String f,
                         @RequestParam(value = "s", required = false) String s, Model model){
        System.out.println("мы в лямбда");
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(f,s));
        System.out.println(list);
        Comparator<String> com = (fi,si)->Integer.compare(fi.length(),si.length());
        list.sort(com);
//
//
//                Comparator<String> com = new Comparator<Integer>(){
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return Integer.compare(o1,o2);
//            }
//        }
        //(f,s)->Integer.compare(f.length(),s.length());
        //list.sort((f,s)->Integer.compare(f.length(),s.length()));
        System.out.println(list);

        return "second/lambda";
    }

}
