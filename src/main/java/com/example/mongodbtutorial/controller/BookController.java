package com.example.mongodbtutorial.controller;

import com.example.mongodbtutorial.entity.Book;
import com.example.mongodbtutorial.service.BookService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;


    @GetMapping("/books")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/books/{id}")
    public Book findById(@PathVariable String id) {
        return bookService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found"));
    }

    @PostMapping("/books")
    public Book save(@RequestBody Book book) {
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return bookService.save(book);
    }

    @DeleteMapping("/books/{id}")
    public void deleteById(@PathVariable String id) {
        bookService.deleteById(id);
    }

    @PatchMapping("/books/{id}")
    public UpdateResult updateFirst(@PathVariable String id,
                                    @RequestBody Book book) throws IllegalAccessException {
        return bookService.updateFirst(id, book);
    }

    @GetMapping("/books/paginated")
    public Page<Book> findAllPaginated(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return bookService.findAllPaginated(page, size);
    }

}
