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
    public String post(){
        return "Posting something here";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String postId(@PathVariable int id){
        return "This is post number: " + id;
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String createPost(){
        return "Creating a post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String postCreated(){
        return "post has been created";
    }

}
