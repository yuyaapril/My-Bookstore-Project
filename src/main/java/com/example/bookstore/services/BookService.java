package com.example.bookstore.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bookstore.models.Role;
import com.example.bookstore.models.Book;
import com.example.bookstore.repos.BookRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService {
  @Autowired
  private final BookRepo bookRepo;
  
  public List<Book> all() {
    List<Book> books = bookRepo.findAll();
    return books;
  }
  
  public void addBook(Book book){
    bookRepo.save(book);
  }
  
  public void updateBook(Book book) {
    bookRepo.save(book);
  }

    public Book getById(Long id) {
    Book dbBook = bookRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("No Book with that id"));
    return dbBook;
  }

  public void drop(Long id) {
    Book dbBook = bookRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("No Book with that id"));
    bookRepo.delete(dbBook);
  }
}
