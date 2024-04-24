package com.example.mongodbtutorial.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("books")
public class Book {
    @Id
    private String id;

    private String title;
    private String author;
    private Integer rating;
    private Integer pages;
    private List<String> genres;
    private List<Review> reviews;
}
