package com.codeup.springblog;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringblogApplication.class)
@AutoConfigureMockMvc
public class PostsIntegrationTests {

    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    PostRepository postDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception{
        testUser = userDao.findByUsername("testUser");

        if(testUser == null){
            User testUser = new User();
            testUser.setUsername("TestUser");
            testUser.setPassword(passwordEncoder.encode("password"));
            testUser.setEmail("testuser@mail.com");
            testUser = userDao.save(testUser);
        }

        httpSession = this.mvc.perform(post("/login").with(csrf())
                .param("username", "TestUser")
                .param("password", "password"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/posts"))
                .andReturn()
                .getRequest()
                .getSession();
    }
    //  SANITY TESTS
    @Test
    public void contextLoads(){
        assertNotNull(mvc);     //  checking MVC bean is working
    }

    @Test
    public void testIfUserSessionIsActive(){
        assertNotNull(httpSession);     //  ensures returned session is not null
    }

    //  CRUD TESTS
    //  R -> READ
    //  list of posts can be shown when visiting /posts
    @Test
    public void testPostsIndex() throws Exception{
        //  grab all posts
        List<Post> posts = postDao.findAll();
        this.mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Community Posts")));
        for(Post post : posts){
            this.mvc.perform(get("/posts"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(post.getTitle())));
        }

    }
    //  single post can be shown when visiting /posts/{id}
//    @Test
//    public void testSinglePostShow() throws Exception{
//        //  grabbing first post by selecting index 0
//        Post existingPost = postDao.findAll().get(0);
//
//        this.mvc.perform(get("/posts/" + existingPost.getId())
//                .session((MockHttpSession) httpSession))
//                .andExpect(status().isOk()) //  checking that post exists
//                .andExpect(content().string(containsString(existingPost.getTitle())))   //  checking that title is not empty
//                .andExpect(content().string(containsString(existingPost.getBody())));   //  checking that body is not empty
//    }
    //  single post can be shown when visiting /posts/{id} when not logged in
    //  security config -> authenticated url: /posts/{id} needs to be commented out
    @Test
    public void testSinglePostShow() throws Exception{
        //  grabbing first post by selecting index 0
        Post existingPost = postDao.findAll().get(0);

        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk()) //  checking that post exists
                .andExpect(content().string(containsString(existingPost.getTitle())))   //  checking that title is not empty
                .andExpect(content().string(containsString(existingPost.getBody())));   //  checking that body is not empty
    }

    //  C -> CREATE
    //  submitting all required params will create post through POST req to /posts/create
    //  httpSession needed for user to be authenticated and create
    @Test
    public void testCreatePost() throws Exception{
        //  Testing without user login
        this.mvc.perform(post("/posts/create")
                        .param("title", "This is just a test post")
                        .param("body", "Will be deleted after tests are completed!"))
                .andExpect(status().isForbidden());
        //  Testing with user login
        this.mvc.perform(post("/posts/create").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("title", "This is just a test post")
                .param("body", "Will be deleted after tests are completed!"))
                .andExpect(status().is3xxRedirection());
    }

    //  U -> UPDATE
    //  post can be updated when submitting POST req to /posts/{id}/edit
    @Test
    public void testEditPost() throws Exception{
        Post existingPost = postDao.findAll().get(0);

        this.mvc.perform(post("/posts/" + existingPost.getId() + "/edit").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("title", "Editing the title")
                .param("body", "edit body"))
                .andExpect(status().is3xxRedirection());

        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Editing the title")));
    }

    //  D -> DELETE
    //  post can be deleted by submitting DELETE request to /posts/{id}/delete
    @Test
    public void testDeletePost() throws Exception{
        if(postDao.findPostByTitle("testPost") == null){
            this.mvc.perform(post("/posts/create").with(csrf())
                    .session((MockHttpSession) httpSession)
                    .param("title", "testPost")
                    .param("body", "testingBody"))
                    .andExpect(status().is3xxRedirection());
        }

        Post deletedPost = postDao.findPostByTitle("testPost");
        this.mvc.perform(get("/posts/" + deletedPost.getId() + "/delete").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("id", String.valueOf(deletedPost.getId())))
                .andExpect(status().is3xxRedirection());
    }

}
