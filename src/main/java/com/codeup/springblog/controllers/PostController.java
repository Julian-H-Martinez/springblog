package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    //  injecting PostRepository into PostController
    //  These two next steps are often called dependency injection,
    //      where we create a Repository instance and
    //      initialize it in the controller class constructor.
        //  PROPERTY
      private PostRepository postsDao;
      private UserRepository userDao;
      //    CONTROLLER
      public PostController(){}
      public PostController(PostRepository postsDao){
          this.postsDao = postsDao;
      }
      public PostController(PostRepository postsDao, UserRepository userDao){
          this.postsDao = postsDao;
          this.userDao = userDao;
      }
//      public UserController(UserRepository userDao){
//          this.userDao = userDao;
//      }

    @GetMapping("/posts")
    public String viewPost(Model model){
          List<Post> allPosts = new ArrayList<>();
          Post titansPost = new Post(1, "Titans Year 2022", "Bold prediction for 2022 NFL Season, Tennessee Titans will win the Super Bowl!");
          Post cowboysPost = new Post(2, "Cowboys Super Bowl or Bust", "Cowboys need a trip to the Super Bowl or that's it for Big Mike McCarthy!");
          Post spursPost = new Post(3, "Spurs Making Moves", "Looks like the Spurs made quite the splash before trade deadline! Chanpionship push?");
          
          allPosts.add(titansPost);
          allPosts.add(cowboysPost);
          allPosts.add(spursPost);

          model.addAttribute("posts", allPosts);
        return "/posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postDetails(@PathVariable long id, Model model){
        Post post1 = new Post(10, "Learning New Things", "Currently in a Full-Stack Web Developer for Java at Codeup! Each day is a learning " +
                "experience" +
                " and look forward to continuing my growth!");
        model.addAttribute("singlePost", post1);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String showCreateForm(){
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String postCreateForm(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body){
//        User user = userDao.getById(1L);
        Post post = new Post();
        post.setTitle("Testing");
        post.setBody("Body of test");
//        post.setUser(user);
        Post userPost = new Post(title, body);
        postsDao.save(userPost);
          return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable long id, Model model){
          Post postToEdit = postsDao.getById(id);
          model.addAttribute("postToEdit", postToEdit);
          return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String submitEdit(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body, @PathVariable long id){
          Post editPost = postsDao.getById(id);
          editPost.setTitle(title);
          editPost.setBody(body);

          postsDao.save(editPost);
          return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id){
          Post deletePost = postsDao.getById(id);
          postsDao.delete(deletePost);
          return "redirect:/posts";
    }
}
