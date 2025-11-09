package com.example.bookstore.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public String handleException(Exception e, Model model) {
    model.addAttribute("errorMessage", e.getMessage());
    return "error";
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  public String hadleCategoryNotFoundException(Exception e, Model model) {
    model.addAttribute("errorMessage", e.getMessage());
    model.addAttribute("errorCode", "404");
    return "error";
  }
  
}
