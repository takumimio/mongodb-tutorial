package com.example.mongodbtutorial.service;

import com.example.mongodbtutorial.entity.Book;
import com.example.mongodbtutorial.repository.BookRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Book> findAll() {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "author"));
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return bookRepository.save(book);
    }

    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    public UpdateResult updateFirst(String id, Book book) throws IllegalAccessException {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        setUpdateModifier(book, update);
        return mongoTemplate.updateFirst(query, update, Book.class);
    }

    private static void setUpdateModifier(Book book, Update update) throws IllegalAccessException {
        Field[] fields = Book.class.getDeclaredFields(); // Get fields specific to the Book class

        for (Field field : fields) {
            field.setAccessible(true); // Enable access to private fields
            Object value = field.get(book); // Retrieve the value of the field from the book object

            if (value != null) {
                update.set(field.getName(), value);
            }
        }
    }

    public Page<Book> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable);
    }
}
