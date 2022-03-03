package com.codeup.springblog;

import com.codeup.springblog.models.Book;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.BookRepository;
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

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

//  annotations to notify spring of what this class does
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringblogApplication.class)
@AutoConfigureMockMvc
public class BooksIntegrationTests {

    private User testUser;
    private HttpSession httpSession;    //  for security purpose

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    BookRepository bookDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception{
        testUser = userDao.findByUsername("testUser");

        //  Creates the test user if not exists
        if(testUser == null){
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("password"));
            newUser.setEmail("testuser@mail.com");
            testUser = userDao.save(newUser);
        }

        //  Ensure that once user is logged in they get redirected correctly
        // Throws a Post request to /login and expect a redirection to the Ads index page after being logged in
        httpSession = this.mvc.perform(post("/login").with(csrf())
                .param("username", "testUser")          //  checking if they matcht
                .param("password", "password"))
                .andExpect(status().is(HttpStatus.FOUND.value()))       //  status 200 if everything is correct
                .andExpect(redirectedUrl("/posts"))         //  does it get redirected
                .andReturn()
                .getRequest()
                .getSession();
    }

    //  sanity test => narrow regression tests
    //  testing that context loads
    //  ensure MVC bean is working/communicating correctly
    //  httpsession is actually working
    //  SANITY TESTS
    @Test
    public void contextLoads(){
        //  Sanity Test, to make sure MVC bean is working
        assertNotNull(mvc);
    }

    @Test
    public void testIfUserSessionIsActive(){
        //  makes sure returned session is not null
        assertNotNull(httpSession);
    }

    //  CRUD FUNCTIONALITY TESTING
    @Test
    public void testCreateBook() throws Exception{
        //  make post request to /books/create
        //  expect redirect to the book
        this.mvc.perform(post("/books/create").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("title", "Alice in Wonderland")
                .param("genre_id", "6"))
                .andExpect(status().is3xxRedirection());    //  is3xxRedirection() -> redirect to specific id of book created
    }

    //  READ
    @Test
    public void testShowBook() throws Exception{
        //  grabbing first index from Book list
        Book existingBook = bookDao.findAll().get(0);

        //  make GET request to /books/{id}
        //  expect redirection to book show page
        this.mvc.perform(get("/books/" + existingBook.getId()))
                .andExpect(status().isOk())
                //  Test dynamic content of page
                .andExpect(content().string(containsString(existingBook.getTitle())));
    }
    @Test
    public void testBooksIndex() throws Exception{
        //  grabbing all books
        Book existingBooks = bookDao.findAll().get(0);

        this.mvc.perform(get("/books"))
                .andExpect(status().isOk())
                //  check static content of page
                .andExpect(content().string(containsString("Notable Books")))
                //  check dynamic content of page
                .andExpect(content().string(containsString(existingBooks.getTitle())));
    }

    //  UPDATE
    @Test
    public void testBookEdit() throws Exception{
        Book existingBook = bookDao.findAll().get(0);

        //  make post request to books/{id}/edit
        //  redirect to show edited book's page
        this.mvc.perform(post("/books/" + existingBook.getId() + "/edit").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("title", "edited title"))
                .andExpect(status().is3xxRedirection());
        //  make get request to books/{id}/edit
        //  redirect to show book page
        this.mvc.perform(get("/books/" + existingBook.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("edited title")));
    }

    //  DELETE
    @Test
    public void testDeleteBook() throws Exception{
        if(bookDao.findBookByTitle("testBook") == null){
            //  adding book to get deleted
            this.mvc.perform(post("/books/create").with(csrf())
                            .session((MockHttpSession) httpSession)
                            .param("title", "testBook"))
                    .andExpect(status().is3xxRedirection());
        }
        //  getting book to delete
        Book bookToDelete = bookDao.findBookByTitle("testBook");

        this.mvc.perform(get("/books/" + bookToDelete.getId() + "/delete").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("id", String.valueOf(bookToDelete.getId())))
                .andExpect(status().is3xxRedirection());
    }
}
