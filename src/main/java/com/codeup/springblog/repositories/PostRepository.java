package com.codeup.springblog.repositories;

import com.codeup.springblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

//  interface needs to extend JpaRepository
//  will need to grab the Objects (Post) and Id datatype (Long)
//  just defining interface that extends JpaRepo allows us to start using in other classes
public interface PostRepository extends JpaRepository <Post, Long>{
    Post findPostByTitle(String title);
}
