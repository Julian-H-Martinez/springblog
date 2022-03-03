package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PostController {

    //  injecting PostRepository into PostController
    //  These two next steps are often called dependency injection,
    //      where we create a Repository instance and
    //      initialize it in the controller class constructor.
        //  PROPERTY
      private PostRepository postsDao;
      private UserRepository userDao;
      private final EmailService emailService;
      //    CONTROLLER
      public PostController(PostRepository postsDao, UserRepository userDao, EmailService emailService){
          this.postsDao = postsDao;
          this.userDao = userDao;
          this.emailService = emailService;
      }

    //  R -> READ
    @GetMapping("/posts")
    public String viewPost(Model model){
          model.addAttribute("posts", postsDao.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postDetails(@PathVariable long id, Model model){
        model.addAttribute("singlePost", postsDao.getById(id));
        return "posts/show";
    }

    //  C -> CREATE
    @GetMapping("/posts/create")
    public String showCreateForm(Model model) {
          model.addAttribute("post", new Post());
          return "posts/create";
    }

    @PostMapping("/posts/create")
    public String postCreateForm(@ModelAttribute Post post){
          User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          post.setUser(userDao.getById(loggedInUser.getId()));
          emailService.prepareAndSend(post, "New Post", "You just posted to the blog.");
          postsDao.save(post);
          return "redirect:/posts";
    }

    //  U -> UPDATE/EDIT
    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable long id, Model model){
          Post postToEdit = postsDao.getById(id);
          User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          if(postToEdit.getUser().getId() == loggedInUser.getId()){
              model.addAttribute("edit", postToEdit);
              return "posts/edit";
          }else{
              return "redirect:/posts";
          }
    }

    // We can access the values submitted from the form using our @RequestParam annotation
    @PostMapping("/posts/{id}/edit")
    public String submitEdit(@ModelAttribute Post post, @PathVariable long id){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          if(postsDao.getById(id).getUser().getId() == loggedInUser.getId()){
              post.setUser(userDao.getById(loggedInUser.getId()));
              emailService.prepareAndSend(post, "Post Edit", "You just edited a blog post.");
              postsDao.save(post);
              return "redirect:/posts";
          }
          return "redirect:/posts";
    }

    //  D -> DELETE

    // For now, we need to use a GetMapping, that way, when we visit the page,
    // our app can access the path variable,
    // then delete the post,
    // then redirect us back to the post index page.
    @GetMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id){
          Post deletePost = postsDao.getById(id);
          postsDao.delete(deletePost);
          return "redirect:/posts";
    }
}
