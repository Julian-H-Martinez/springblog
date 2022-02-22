package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//  let spring know we will have a req/res body
//  defines that our class is a controller
@Controller
public class HelloController {
    //  multiple getMappings allowed withing the class

    //  urlPattern that we are using
    //  defines a method that should be invoked when a GET request is received for the specified URI
    @GetMapping("/hello")
    //  tells Spring that whatever is returned from this method should be the body of our response
    @ResponseBody
    public String hello(){
        return "<h1>Hello from Spring!</h1>";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "test";
    }

    //  use curly braces to establish Path Variables in the mapping definition
    @GetMapping("/hello/{name}")
    @ResponseBody
    //  use annotation for Path Variable
    //    If the path variable we are looking for is not a string, we can simply define the parameter with a different type.
    public String helloName(@PathVariable String name){
        return "<h1>Hello, " + name + "!</h1>";
    }

    //  REQUEST MAPPING
    @RequestMapping(path = "/increment/{number}", method = RequestMethod.GET)
    @ResponseBody
    public String increment(@PathVariable int number){
        return number + " plus one is " + (number + 1) + "!";
    }

}


