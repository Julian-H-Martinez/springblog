package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Book;
import com.codeup.springblog.models.Genre;
import com.codeup.springblog.repositories.AuthorRepository;
import com.codeup.springblog.repositories.BookRepository;
import com.codeup.springblog.repositories.GenreRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    private BookRepository bookDao;
    private AuthorRepository authorsDao;
    private GenreRepository genresDao;
    private final EmailService emailService;

    public BookController(BookRepository bookDao, AuthorRepository authorsDao, GenreRepository genresDao, EmailService emailService) {
        this.bookDao = bookDao;
        this.authorsDao = authorsDao;
        this.genresDao = genresDao;
        this.emailService = emailService;
    }

    @GetMapping("/books")
    public String showBooks(Model model) {
        model.addAttribute("allBooks", bookDao.findAll());
        return "books/index";
    }

    @GetMapping("/books/{id}")
    public String showBook(@PathVariable long id, Model model){
        model.addAttribute("singleBook", bookDao.getById(id));
        return "books/showOne";
    }

    @GetMapping("/books/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("genres", genresDao.findAll());
        return "books/create";
    }

    //  we can now tell form to expect object because of request in showCreateForm
    @PostMapping("/books/create")
    public String createBook(@ModelAttribute Book book) {
        book.setAuthor(authorsDao.getById(1L));
        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        model.addAttribute("bookToEdit", bookDao.getById(id));
        return "books/edit";
    }

    //  we can now tell form to expect object because of request in showCreateForm
    @PostMapping("/books/{id}/edit")
    public String editBook(@ModelAttribute Book book, @PathVariable long id) {
        book.setAuthor(authorsDao.getById(1L));
        bookDao.save(book);
        return "redirect:/books/" + id;
    }

    //  DELETE
    @GetMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable long id){
        bookDao.delete(bookDao.getById(id));
        return "redirect:/books";
    }

    @GetMapping("/send-email")
    public String sendEmail(){
        emailService.prepareAndSend("Testing", "Did this work");
        return "redirect:/";
    }
}
