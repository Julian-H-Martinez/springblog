package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    @GetMapping("/posts")
    public String viewPost(Model model){
        Post[] posts = {
                new Post(1,"Getting things Done", "Trying to get things done"),
                new Post(2,"Trying this out", "Because nothing else is working")
        };
        model.addAttribute("post1", posts[0]).toString();
        model.addAttribute("post2", posts[1]).toString();
        return "/posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postDetails(@PathVariable long id, Model model){
        Post post1 = new Post("Learning New Things", "Currently in a Full-Stack Web Developer for Java at Codeup! Each day is a learning " +
                "experience" +
                " and look forward to continuing my growth!");
        model.addAttribute("postTitle", post1.getTitle());
        model.addAttribute("postBody", post1.getBody());
//        model.addAttribute("post", post1);
        return "posts/show";
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
