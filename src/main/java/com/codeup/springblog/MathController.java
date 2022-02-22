package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MathController {
    @GetMapping("/add/{num}/and/{num2}")
    @ResponseBody
    public String add(@PathVariable int num, @PathVariable int num2){
        return num + " plus " + num2 + " equals: " + (num + num2);
    }

    @GetMapping("/subtract/{num2}/from/{num}")
    @ResponseBody
    public String subtract(@PathVariable int num, @PathVariable int num2){
        return num2 + " minus " + num + " equals: " + (num2 - num);
    }

    @GetMapping("/multiply/{num}/and/{num2}")
    @ResponseBody
    public String multiply(@PathVariable int num, @PathVariable int num2){
        return num + " times " + num2 + " equals: " + (num * num2);
    }

    @GetMapping("/divide/{num}/by/{num2}")
    @ResponseBody
    public String divide(@PathVariable int num, @PathVariable int num2){
        return String.format("<h1>%d divided by %d equals: %d</h1>", num, num2, (num/num2));
//        return num + " divided by " + num2 + " equals: " + (num / num2);
    }

}
