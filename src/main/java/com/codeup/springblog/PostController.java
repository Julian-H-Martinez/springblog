package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

    @GetMapping("/posts")
    @ResponseBody
    public String viewPost(){
        return "Viewing Index of all Posts";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String postDetails(@PathVariable long id){
        return "Individual Post Number: " + id;
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String showCreateForm(){
        return "Viewing the form to create post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String postCreateForm(){
        return "create new post";
    }

}
