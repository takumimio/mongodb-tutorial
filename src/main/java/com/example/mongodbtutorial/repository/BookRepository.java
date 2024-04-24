package com.example.mongodbtutorial.repository;

import com.example.mongodbtutorial.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookRepository extends MongoRepository<Book, String> {

    @Query("{title:'?0'}")
    Book findBooksdByTitle1(String title);

    public Book findByTitle(String title);



}
