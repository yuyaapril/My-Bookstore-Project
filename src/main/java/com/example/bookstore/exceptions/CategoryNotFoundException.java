package com.example.bookstore.exceptions;

public class CategoryNotFoundException extends RuntimeException{
  public CategoryNotFoundException(String message) {
    super(message);
  }
}
