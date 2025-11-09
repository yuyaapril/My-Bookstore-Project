package com.example.bookstore.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.bookstore.dtos.BookDto;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Category;
import com.example.bookstore.services.BookService;
import com.example.bookstore.services.CatService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;






@Controller
@AllArgsConstructor
@RequestMapping("/books")

public class BookController {
  public static final String UPLOAD_DIR = "src/main/resources/static/imgs";
  @Autowired
  private final BookService bookService;

  @Autowired
  private final CatService catService;

  @GetMapping("")
  public String allBooks(Model model) {
    List<Book> books = bookService.all();
    model.addAttribute("books", books);
     List<Category> cats = catService.getAll();
    model.addAttribute("cats", cats);
    return "book/all";
  }
  @GetMapping("add")
  public String toBookAddPage(Model model) {
    List<Category> cats = catService.getAll();
    model.addAttribute("cats", cats);
    return "book/add";
  }
  
  private String saveFile(MultipartFile file) {
    try{
      Path uploadPath = Paths.get(UPLOAD_DIR);
      if(!Files.exists(uploadPath)){
        Files.createDirectories(uploadPath);
      }
      String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(file.getInputStream(), filePath);
      return fileName;
    }
    catch (IOException e) {
      System.out.println("Error Saving File" + e.getMessage());
      return null;
    }
    
  }
  @PostMapping("add")
  public String addBook(@ModelAttribute BookDto bookDto) {
    Book book = new Book();
    if (!bookDto.getCoverImage().isEmpty()) {
      String fileName = saveFile(bookDto.getCoverImage());
      book.setCoverImageUrl(fileName);
    }
    book.setTitle(bookDto.getTitle());
    book.setAuthor(bookDto.getAuthor());
    book.setPublicationDate(bookDto.getPublicationDate());
    book.setDescription(bookDto.getDescription());
    book.setPrice(bookDto.getPrice());
    book.setStock(bookDto.getStock());

    Category cat = catService.getById(bookDto.getCategoryId());
    book.setCategory(cat);
    bookService.addBook(book);
    return "redirect:/books";
  }
  
  @GetMapping("edit/{id}")
  public String bookEdit(@PathVariable Long id, Model model) {
    Book book = bookService.getById(id);

    List<Category> cats = catService.getAll();
    model.addAttribute("cats", cats);
    model.addAttribute("book", book);
    return "book/edit";
  }
  @PostMapping("edit/{id}")
  public String editBook(@PathVariable Long id, @ModelAttribute BookDto bookDto) {

    Book book = bookService.getById(id);

    book.setTitle(bookDto.getTitle());
    book.setAuthor(bookDto.getAuthor());
    book.setPublicationDate(bookDto.getPublicationDate());
    book.setDescription(bookDto.getDescription());
    book.setPrice(bookDto.getPrice());
    book.setStock(bookDto.getStock());
    book.setCategory(catService.getById(bookDto.getCategoryId()));

    if (!bookDto.getCoverImage().isEmpty()) {
      String fileName = saveFile(bookDto.getCoverImage());
      book.setCoverImageUrl(fileName);
    }
    bookService.updateBook(book);
    return "redirect:/books";
  }
  
  @GetMapping("bycat/{id}")
  public String bookByCat(@PathVariable Integer id, Model model) {
    Category category = catService.getById(id);
    Set<Book> books = category.getBooks();
    List<Category> cats = catService.getAll();
    model.addAttribute("cats", cats);
    model.addAttribute("books", books);
      return "book/all";
  }
  @GetMapping("drop/{id}")
  public String deletebook(@PathVariable Long id) {
      bookService.drop(id);
      return "redirect:/books";
  }
  
  
  
}
