package com.example.bookstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookstore.models.Category;
import com.example.bookstore.services.CatService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
@RequestMapping("/cats")
public class CatController {
  @Autowired
  private final CatService catService;

  @GetMapping("")
  public String all(Model model) {
    List<Category> cats = catService.getAll();
    model.addAttribute("cats", cats);
    return "category/all";
  }

  @GetMapping("add")
  public String catsPage() {
    return "category/add";
  }
  
  @PostMapping("add")
  public String addCats(@RequestParam String name) {
    catService.add(name);
    return "redirect:/cats";
  }

    @GetMapping("edit/{id}")
    public String editCats(@PathVariable Integer id, Model model) {
      Category cat = catService.getById(id);
      model.addAttribute("cat", cat);
      return "category/edit";
    }
  
    @PostMapping("edit/{id}")
    public String updateCats(@PathVariable Integer id, @RequestParam String name) {
      catService.updateCat(id, name);
      return "redirect:/cats";
    }

    @GetMapping("drop/{id}")
    public String dropCats(@PathVariable Integer id) {
      catService.drop(id);
      return "redirect:/cats";
    }
    
  
}
