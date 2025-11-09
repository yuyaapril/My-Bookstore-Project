package com.example.bookstore.services;

import java.util.List;

import org.hibernate.boot.archive.scan.spi.ClassDescriptor.Categorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bookstore.models.Role;
import com.example.bookstore.exceptions.CategoryNotFoundException;
import com.example.bookstore.models.Category;
import com.example.bookstore.repos.CatRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CatService {
  @Autowired
  private final CatRepo catRepo;
  //allcategory
  public List<Category> getAll() {
    List<Category> cats = catRepo.findAll();
    return cats;
  }
 //add category
 public void add(String name) {
   Category cat = new Category();
   cat.setName(name);
   catRepo.save(cat);
 }
   public Category getById(Integer id) {
     Category category = catRepo.findById(id)
         .orElseThrow(() -> new CategoryNotFoundException("No Role with that id"));
    // Category category = catRepo.findById(id)
    //.orElseThrow(() -> new RuntimeException("No Role with that id"));
    return category;
  }
  
 public void updateCat(Integer id, String name) {
   Category category = catRepo.findById(id)
       .orElseThrow(() -> new RuntimeException("No Category with that id"));
   if (category != null) {
     category.setName(name);
     catRepo.save(category);
   }
 }
  public void drop(Integer id) {
   Category category = catRepo.findById(id)
       .orElseThrow(() -> new RuntimeException("No Category with that id"));
   if (category != null) {
     catRepo.delete(category);
   }
 }  
}
